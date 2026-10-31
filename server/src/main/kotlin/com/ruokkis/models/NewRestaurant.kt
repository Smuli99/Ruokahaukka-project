package com.ruokkis.models

import kotlinx.serialization.Serializable

@Serializable
data class NewRestaurant(
    val name: String,
    val url: String,
    val campus: String?
)