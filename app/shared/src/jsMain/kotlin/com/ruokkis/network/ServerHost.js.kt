package com.ruokkis.network

import kotlinx.browser.window

class WebServerHost: ServerHost {
    override val port: Int = 8080
    override val host: String = window.location.hostname
}

actual fun getServerHost(): ServerHost = WebServerHost()
