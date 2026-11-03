package com.ruokkis.config

data class RestaurantSource(
    val name: String,
    val url: String,
)

// Tähän lisätään osoitteet
object RestaurantDataSources {
    val piato = RestaurantSource(
        name = "piato",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1408&language=fi"
    )

    val rentukka = RestaurantSource(
        name = "rentukka",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1416&language=fi"
    )

    val ilokivi = RestaurantSource(
        name = "ilokivi",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1419&language=fi"
    )

    val all = listOf(
        piato,
        rentukka,
        ilokivi,
    )
}