package com.nuvio.app.features.addons

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AddonPlayerEventsTest {

    @Test
    fun playerUrlKeepsTheStremioExtraSegmentReadable() {
        assertEquals(
            "https://addon.example/player/series/tt1234567%3A1%3A2/action=progress&currentTime=30000&duration=120000.json",
            buildPlayerEventUrl(
                manifestUrl = "https://addon.example/manifest.json",
                type = "series",
                videoId = "tt1234567:1:2",
                action = "progress",
                currentTimeMs = 30_000L,
                durationMs = 120_000L,
            ),
        )
    }

    @Test
    fun playerUrlKeepsTheManifestQuery() {
        assertEquals(
            "https://addon.example/player/movie/tt0000001/action=start.json?token=abc",
            buildPlayerEventUrl(
                manifestUrl = "https://addon.example/manifest.json?token=abc",
                type = "movie",
                videoId = "tt0000001",
                action = "start",
                currentTimeMs = null,
                durationMs = null,
            ),
        )
    }

    @Test
    fun blankTransportIsRejected() {
        assertNull(
            buildPlayerEventUrl(
                manifestUrl = "   ",
                type = "movie",
                videoId = "tt1",
                action = "start",
                currentTimeMs = null,
                durationMs = null,
            ),
        )
    }

    @Test
    fun typeNamesCollapseToMovieOrSeries() {
        assertEquals("movie", normalizePlayerEventType("movies"))
        assertEquals("series", normalizePlayerEventType("TV"))
        assertEquals("movie", normalizePlayerEventType(null))
    }

    @Test
    fun playerResourceMatchesTypeAndIdPrefix() {
        val manifest = manifest(
            resources = listOf(AddonResource(name = "player", types = listOf("series"), idPrefixes = listOf("tt"))),
        )
        assertTrue(addonSupportsPlayer(manifest, "series", "tt123:1:2"))
        assertFalse(addonSupportsPlayer(manifest, "movie", "tt123"))
        assertFalse(addonSupportsPlayer(manifest, "series", "kitsu:1"))
    }

    @Test
    fun emptyPlayerTypesAndPrefixesAcceptTheRequest() {
        val manifest = manifest(
            resources = listOf(AddonResource(name = "Player", types = emptyList())),
        )
        assertTrue(addonSupportsPlayer(manifest, "movie", "tt9"))
    }

    @Test
    fun addonWithoutPlayerResourceIsIgnored() {
        val manifest = manifest(
            resources = listOf(AddonResource(name = "stream", types = listOf("movie"))),
        )
        assertFalse(addonSupportsPlayer(manifest, "movie", "tt9"))
    }

    private fun manifest(resources: List<AddonResource>) = AddonManifest(
        id = "com.example.player",
        name = "Player",
        description = "",
        version = "1",
        resources = resources,
        types = listOf("movie", "series"),
        transportUrl = "https://addon.example/manifest.json",
    )
}
