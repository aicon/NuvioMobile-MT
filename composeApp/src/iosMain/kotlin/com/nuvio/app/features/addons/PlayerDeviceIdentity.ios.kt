package com.nuvio.app.features.addons

import platform.Foundation.NSUUID
import platform.Foundation.NSUserDefaults

internal actual object PlayerDeviceIdentity {
    private const val deviceIdKey = "nuvio_player_device_id"

    actual fun deviceId(): String {
        val defaults = NSUserDefaults.standardUserDefaults
        val existing = defaults.stringForKey(deviceIdKey)?.trim().orEmpty()
        if (existing.isNotEmpty()) return existing
        val created = NSUUID().UUIDString
        defaults.setObject(created, deviceIdKey)
        return created
    }

    actual fun deviceName(): String {
        val model = platform.UIKit.UIDevice.currentDevice.model.trim()
        return if (model.isEmpty()) "telefon" else "telefon · $model"
    }
}
