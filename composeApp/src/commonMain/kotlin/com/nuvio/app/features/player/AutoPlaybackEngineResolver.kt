package com.nuvio.app.features.player

/**
 * Mirrors NuvioTV-MT: anime and AVI go to libmpv, movies and ordinary TV to ExoPlayer.
 * HDR/Dolby Vision stays on ExoPlayer. A saved ExoPlayer engine still yields to libmpv for AVI
 * when the session has no manual engine override. A saved libmpv engine is left alone.
 */
internal fun resolveAutoPlaybackEngine(
    playbackEngineSetting: AndroidPlaybackEngine,
    videoId: String?,
    streamFilename: String?,
    streamName: String?,
    streamDescription: String?,
    contentTitle: String?,
    genres: List<String>,
    country: String?,
    streamUrl: String? = null,
): AndroidPlaybackEngine? {
    val streamMetadataText = buildString {
        streamFilename?.let { appendLine(it) }
        streamName?.let { appendLine(it) }
        streamDescription?.let { appendLine(it) }
        append(contentTitle.orEmpty())
    }
    val hdr = HDR_OR_DOLBY_VISION_REGEX.containsMatchIn(streamMetadataText)
    val avi = looksLikeAviContainer(
        filename = streamFilename,
        streamName = streamName,
        streamDescription = streamDescription,
        streamUrl = streamUrl,
        metadataText = streamMetadataText,
    )

    if (playbackEngineSetting == AndroidPlaybackEngine.Libmpv) return null
    if (playbackEngineSetting == AndroidPlaybackEngine.ExoPlayer) {
        return if (!hdr && avi) AndroidPlaybackEngine.Libmpv else null
    }

    // HDR / Dolby Vision takes precedence: libmpv would improperly tonemap it to SDR.
    if (hdr) return AndroidPlaybackEngine.ExoPlayer
    if (avi) return AndroidPlaybackEngine.Libmpv

    val hasAnimeId = videoId?.startsWith("kitsu:") == true ||
        videoId?.startsWith("mal:") == true ||
        videoId?.startsWith("anilist:") == true
    if (hasAnimeId) return AndroidPlaybackEngine.Libmpv

    if (genres.any { it.equals("anime", ignoreCase = true) }) {
        return AndroidPlaybackEngine.Libmpv
    }

    val isAnimationFromJapan = genres.any { it.equals("animation", ignoreCase = true) } &&
        isJapan(country)
    if (isAnimationFromJapan) return AndroidPlaybackEngine.Libmpv

    return AndroidPlaybackEngine.ExoPlayer
}

private val HDR_OR_DOLBY_VISION_REGEX = Regex("""(?i)\b(hdr|hdr10\+?|dv|dolby\s*vision)\b""")

private val AVI_TOKEN_REGEX = Regex("""(?i)(?:^|[\s.\-\[(_])(?:avi|xvid)(?:[\s.\-\])_]|$)""")

/**
 * AVI often stutters on ExoPlayer. Detect it from the path, a mime hint, or an AVI/XviD token.
 * HDR/DV is decided by the caller and keeps ExoPlayer.
 */
internal fun looksLikeAviContainer(
    filename: String?,
    streamName: String?,
    streamDescription: String?,
    streamUrl: String?,
    metadataText: String,
): Boolean {
    val candidates = listOfNotNull(filename, streamName, streamDescription, streamUrl, metadataText)
    for (raw in candidates) {
        val value = raw.trim()
        if (value.isEmpty()) continue
        val lower = value.lowercase()
        if (lower.contains("video/avi") || lower.contains("video/x-msvideo")) return true
        val pathOnly = lower.substringBefore('?').substringBefore('#')
        if (pathOnly.endsWith(".avi") || lower.contains(".avi.") || lower.contains(".avi/")) return true
        if (AVI_TOKEN_REGEX.containsMatchIn(value)) return true
    }
    return false
}

// TMDB enrichment rewrites `country` as comma-joined ISO codes ("US, GB"), addon metadata as
// full names ("Japan") — match whole comma-separated tokens.
private fun isJapan(country: String?): Boolean =
    country?.split(',')?.any { token ->
        val value = token.trim()
        value.equals("japan", ignoreCase = true) || value.equals("jp", ignoreCase = true)
    } == true
