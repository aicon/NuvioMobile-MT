package com.nuvio.app.features.addons

/** Stable install id + default label for MediaTrail Now Watching strips. */
internal expect object PlayerDeviceIdentity {
    fun deviceId(): String
    fun deviceName(): String
}
