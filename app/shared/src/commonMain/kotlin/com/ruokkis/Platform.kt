package com.ruokkis

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform