package com.ruokkis.dto

import com.ruokkis.models.NewRestaurant

/**
 * Muunnetaan data halutulla tavalla tietokantaa varten
 * Muuntaa siis datan, jonka RestaurantApiDto parsii
 *
 * tönne lisättävä weekMenu, dayMenu jne...
 */
fun RestaurantDto.toRestaurant() = NewRestaurant(
    name = name,
    url = url,
    campus = null,
)