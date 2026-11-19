package com.ruokkis.dto

import kotlinx.serialization.Serializable

/**
 * Luodaan käytännössä kopiot backendillä olevista data luokista, jotta voidaan käyttää frontendillä
 */
@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val url: String,
    val campus: String?
)