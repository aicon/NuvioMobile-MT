package com.nuvio.app.features.addons

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Fire-and-forget Stremio player events for installed addons that declare the `player` resource.
 * A failure never reaches playback. This is the phone counterpart of NuvioTV-MT's dispatcher.
 */
internal object AddonPlayerEventDispatcher {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun emit(
        action: String,
        mediaType: String,
        videoId: String,
        currentTimeMs: Long?,
        durationMs: Long?,
        enabled: Boolean,
    ) {
        if (!enabled || videoId.isBlank()) return
        val normalizedAction = action.trim().lowercase()
        if (normalizedAction.isEmpty()) return
        val type = normalizePlayerEventType(mediaType)
        scope.launch {
            val targets = AddonRepository.uiState.value.addons.enabledAddons().mapNotNull { addon ->
                val manifest = addon.manifest ?: return@mapNotNull null
                if (!addonSupportsPlayer(manifest, type, videoId)) return@mapNotNull null
                val url = buildPlayerEventUrl(
                    manifestUrl = manifest.transportUrl,
                    type = type,
                    videoId = videoId,
                    action = normalizedAction,
                    currentTimeMs = currentTimeMs,
                    durationMs = durationMs,
                ) ?: return@mapNotNull null
                url
            }
            for (url in targets) {
                runCatching {
                    withTimeoutOrNull(PLAYER_EVENT_TIMEOUT_MS) {
                        fetchAddonResponseText(url)
                    }
                }
            }
        }
    }
}

internal fun normalizePlayerEventType(contentType: String?): String {
    val raw = contentType?.trim()?.lowercase().orEmpty()
    return when {
        raw == "movie" || raw == "movies" -> "movie"
        raw == "series" || raw == "tv" || raw == "show" || raw == "shows" -> "series"
        raw.isBlank() -> "movie"
        else -> raw
    }
}

internal fun addonSupportsPlayer(manifest: AddonManifest, type: String, videoId: String): Boolean {
    val playerResources = manifest.resources.filter { it.name.equals("player", ignoreCase = true) }
    if (playerResources.isEmpty()) return false
    val typeOk = playerResources.any { resource ->
        resource.types.isEmpty() || resource.types.any { it.equals(type, ignoreCase = true) }
    }
    if (!typeOk) return false
    val prefixes = playerResources.flatMap { it.idPrefixes }.ifEmpty { manifest.idPrefixes }
    if (prefixes.isEmpty()) return true
    return prefixes.any { prefix -> videoId.startsWith(prefix) }
}

internal fun buildPlayerEventUrl(
    manifestUrl: String,
    type: String,
    videoId: String,
    action: String,
    currentTimeMs: Long?,
    durationMs: Long?,
): String? {
    val base = addonTransportBaseUrl(manifestUrl).trim().trimEnd('/')
    if (base.isEmpty() || videoId.isBlank()) return null
    val query = manifestUrl.substringAfter("?", "").let { value ->
        if (value.isBlank()) "" else "?$value"
    }
    val extras = buildString {
        append("action=").append(action.encodeAddonPathSegment())
        if (currentTimeMs != null) append("&currentTime=").append(currentTimeMs)
        if (durationMs != null) append("&duration=").append(durationMs)
    }
    return "$base/player/$type/${videoId.encodeAddonPathSegment()}/$extras.json$query"
}

private const val PLAYER_EVENT_TIMEOUT_MS = 2_500L
