package com.ruokkis.network

import android.os.Build

class AndroidServerHost: ServerHost {
    override val port: Int = 8080
    override val host: String = "localhost"
//    override val host: String =
//        if (isEmulator()) "10.0.2.2" else "10.3.22.46"

    private fun isEmulator(): Boolean =
                Build.HARDWARE.contains("goldfish") ||
                Build.HARDWARE.contains("ranchu") ||
                Build.PRODUCT.contains("sdk") ||
                Build.PRODUCT.contains("emulator") ||
                Build.MODEL.contains("sdk_gphone") ||
                Build.MODEL.contains("Emulator") ||
                Build.FINGERPRINT.contains("generic")
}

actual fun getServerHost(): ServerHost = AndroidServerHost()


