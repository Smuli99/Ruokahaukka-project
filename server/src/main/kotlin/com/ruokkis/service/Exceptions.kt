package com.ruokkis.service

// Virheiden käsittelyä varten

// Jos ravintola ei löydy
class RestaurantNotFoundException(val id: String?) : RuntimeException("Unknown restaurant $id")

// Jos palvelu kaatuu
class UpstreamException(cause: Throwable) : RuntimeException(cause)