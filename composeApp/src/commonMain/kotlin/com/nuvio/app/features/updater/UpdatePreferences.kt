package com.nuvio.app.features.updater

import com.nuvio.app.core.build.AppVersionConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UpdatePreferences(
    @Suppress("UNUSED_PARAMETER") versionName: String = AppVersionConfig.VERSION_NAME,
) {
    // Nuvio-MT ships Stable-only builds; ignore any previously stored beta channel.
    private val _channel = MutableStateFlow(UpdateChannel.STABLE)
    val channel = _channel.asStateFlow()

    init {
        if (AppUpdaterPlatform.isSupported) {
            AppUpdaterPlatform.setUpdateChannel(UpdateChannel.STABLE.storedValue)
        }
    }

    fun setChannel(channel: UpdateChannel) {
        // Channel UI is removed; keep Stable even if something still calls this.
        if (channel != UpdateChannel.STABLE) return
        if (_channel.value == UpdateChannel.STABLE) return
        AppUpdaterPlatform.setUpdateChannel(UpdateChannel.STABLE.storedValue)
        AppUpdaterPlatform.setIgnoredTag(null)
        _channel.value = UpdateChannel.STABLE
    }

    companion object {
        val shared by lazy { UpdatePreferences() }
    }
}
