package com.ruokkis.network

import platform.Foundation.NSProcessInfo

class IOSServerHost: ServerHost {
    override val port: Int = 8080
    override val host: String =
        if(NSProcessInfo.processInfo.environment["SIMULATOR_DEVICE_NAME"] != null)
            "localhost" else
                "10.3.22.46"
}

actual fun getServerHost(): ServerHost = IOSServerHost()