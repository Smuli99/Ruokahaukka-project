package com.ruokkis.network

interface ServerHost {
    val host: String
    val port: Int
}

expect fun getServerHost(): ServerHost