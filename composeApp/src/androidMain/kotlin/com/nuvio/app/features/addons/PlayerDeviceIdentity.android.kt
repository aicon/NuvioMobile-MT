package com.nuvio.app.features.addons

import android.content.Context
import android.os.Build
import java.util.UUID

internal actual object PlayerDeviceIdentity {
    private const val preferencesName = "nuvio_player_device"
    private const val deviceIdKey = "device_id"

    private var preferences: android.content.SharedPreferences? = null

    fun initialize(context: Context) {
        preferences = context.applicationContext.getSharedPreferences(
            preferencesName,
            Context.MODE_PRIVATE,
        )
    }

    actual fun deviceId(): String {
        val prefs = preferences ?: return "legacy"
        val existing = prefs.getString(deviceIdKey, null)?.trim().orEmpty()
        if (existing.isNotEmpty()) return existing
        val created = UUID.randomUUID().toString()
        prefs.edit().putString(deviceIdKey, created).apply()
        return created
    }

    actual fun deviceName(): String {
        val model = Build.MODEL.trim()
        return if (model.isEmpty()) "telefon" else "telefon · $model"
    }
}
