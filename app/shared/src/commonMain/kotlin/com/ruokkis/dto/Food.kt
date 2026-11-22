package com.ruokkis.dto

import kotlinx.serialization.Serializable

@Serializable
data class Food(
    val id: String,
    val name: String,
    val normalizedName: String,
    val favorite: Boolean,
)
{
}