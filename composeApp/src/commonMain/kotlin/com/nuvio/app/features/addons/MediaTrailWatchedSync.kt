package com.nuvio.app.features.addons

import com.nuvio.app.features.watched.WatchedItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Best-effort library mark/unmark to MediaTrail via Bearer POST /nuvio/watched.json.
 * Failures never roll back the local Nuvio mark. Token stays in the Authorization header.
 */
internal object MediaTrailWatchedSync {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun pushMarks(items: Collection<WatchedItem>, played: Boolean) {
        if (items.isEmpty()) return
        val targets = resolveMediaTrailWatchedTargets(
            AddonRepository.uiState.value.addons.enabledAddons(),
        )
        if (targets.isEmpty()) return
        val payloads = items.mapNotNull { item -> watchedPayloadJson(item, played = played) }
        if (payloads.isEmpty()) return
        scope.launch {
            for (target in targets) {
                for (body in payloads) {
                    runCatching {
                        withTimeoutOrNull(WATCHED_SYNC_TIMEOUT_MS) {
                            httpPostJsonWithHeaders(
                                url = target.watchedUrl,
                                body = body,
                                headers = mapOf(
                                    "Authorization" to "Bearer ${target.bearerToken}",
                                    "Accept" to "application/json",
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

internal data class MediaTrailWatchedTarget(
    val watchedUrl: String,
    val bearerToken: String,
)

internal fun resolveMediaTrailWatchedTargets(addons: List<ManagedAddon>): List<MediaTrailWatchedTarget> =
    addons.mapNotNull { addon ->
        val url = addon.manifest?.transportUrl ?: addon.manifestUrl
        parseMediaTrailWatchedEndpoint(url)
    }.distinctBy { it.watchedUrl to it.bearerToken }

/**
 * Addon installs look like `https://host/nuvio/<token>/manifest.json`.
 * Watched sync uses the host origin + Bearer token — never the path token form.
 */
internal fun parseMediaTrailWatchedEndpoint(manifestUrl: String): MediaTrailWatchedTarget? {
    val path = manifestUrl.substringBefore("?").trim().trimEnd('/')
    if (path.isEmpty()) return null
    val match = MEDIA_TRAIL_MANIFEST_URL.matchEntire(path) ?: return null
    val origin = match.groupValues[1]
    val token = match.groupValues[2].trim()
    if (token.isEmpty()) return null
    return MediaTrailWatchedTarget(
        watchedUrl = "$origin/nuvio/watched.json",
        bearerToken = token,
    )
}

internal fun watchedPayloadJson(item: WatchedItem, played: Boolean): String? {
    val contentId = item.id.trim()
    if (contentId.isEmpty()) return null
    val type = normalizePlayerEventType(item.type)
    val action = if (played) "watched" else "unwatched"
    if (type == "series") {
        val season = item.season ?: return null
        val episode = item.episode ?: return null
        return buildString {
            append('{')
            appendJsonField("action", action)
            append(',')
            appendJsonField("type", "series")
            append(',')
            appendJsonField("id", contentId)
            append(',')
            append("\"season\":").append(season)
            append(',')
            append("\"episode\":").append(episode)
            if (played && item.markedAtEpochMs > 0L) {
                append(',')
                append("\"watched_at\":").append(item.markedAtEpochMs)
            }
            append('}')
        }
    }
    return buildString {
        append('{')
        appendJsonField("action", action)
        append(',')
        appendJsonField("type", "movie")
        append(',')
        appendJsonField("id", contentId)
        if (played && item.markedAtEpochMs > 0L) {
            append(',')
            append("\"watched_at\":").append(item.markedAtEpochMs)
        }
        append('}')
    }
}

private fun StringBuilder.appendJsonField(name: String, value: String) {
    append('"').append(name).append("\":\"")
    value.forEach { ch ->
        when (ch) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> append(ch)
        }
    }
    append('"')
}

private val MEDIA_TRAIL_MANIFEST_URL = Regex(
    """^(https?://[^/]+)/nuvio/([^/]+)/manifest(?:\.json)?$""",
    RegexOption.IGNORE_CASE,
)

private const val WATCHED_SYNC_TIMEOUT_MS = 4_000L
