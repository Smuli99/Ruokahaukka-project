package com.ruokkis.dto

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val url: String,
    val campus: String?
)