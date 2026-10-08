package com.ruokkis.network

import android.os.Build

class AndroidServerHost: ServerHost {
    override val port: Int = 8080
    override val host: String =
        if (isEmulator()) "10.0.2.2" else "10.3.22.46"

    private fun isEmulator(): Boolean =
        Build.FINGERPRINT.contains("generic") ||
        Build.FINGERPRINT.contains("emulator") ||
        Build.MODEL.contains("Emulator") ||
        Build.MODEL.contains("Android SDK built for")
}

actual fun getServerHost(): ServerHost = AndroidServerHost()


