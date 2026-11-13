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

    val lozzi = RestaurantSource(
        name = "lozzi",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1401&language=fi"
    )

    val tilia = RestaurantSource(
        name = "tilia",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1413&language=fi"
    )

    val uno = RestaurantSource(
        name = "uno",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1414&language=fi"
    )

    val ylisto = RestaurantSource(
        name = "ylisto",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1403&language=fi"
    )

    val kvarkki = RestaurantSource(
        name = "kvarkki",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=140301&language=fi"
    )

    val belvedere = RestaurantSource(
        name = "belvedere",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1404&language=fi",
    )

    val syke = RestaurantSource(
        name = "syke",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1405&language=fi",
    )

    val normaalikoulu = RestaurantSource(
        name = "normaalikoulu",
        url = "https://www.semma.fi/menuapi/feed/json?costNumber=1411&language=fi"
    )

    //    Compass Group

    val fiilu = RestaurantSource(
        name = "fiilu",
        "https://www.compass-group.fi/menuapi/feed/json?costNumber=3364&language=fi",
    )

    val taide = RestaurantSource(
        name = "taide",
        url = "https://www.compass-group.fi/menuapi/feed/json?costNumber=0301&language=fi",
    )

    val bistro = RestaurantSource(
        name = "bistro",
        url = "https://www.compass-group.fi/menuapi/feed/json?costNumber=3081&language=fi",
    )
    val all = listOf(
        piato,
        rentukka,
        ilokivi,
        lozzi,
        tilia,
        uno,
        ylisto,
        kvarkki,
        belvedere,
        syke,
        normaalikoulu,
        fiilu,
        taide,
        bistro,
    )
}