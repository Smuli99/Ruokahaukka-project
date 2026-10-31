package com.ruokkis.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val url: String,
    val campus: String?
)