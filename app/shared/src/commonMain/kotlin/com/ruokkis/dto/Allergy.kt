package com.ruokkis.dto

import kotlinx.serialization.Serializable

@Serializable
data class Allergy(
    val id: String,
    val tag: String,
    val nameFi: String?
)