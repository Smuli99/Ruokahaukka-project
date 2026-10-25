package com.ruokkis

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.testing.testApplication
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.junit.Test
import kotlin.test.*


// Kun uusia ravintoloita lisätään niin niiden routeille tehdään testit samaan tapaan
// ainoastaan get pyynnössä muuttuu ravintolan id esim /piato, /taide ...
class RoutingTest {

    private val host = "https://www.semma.fi/"
    private val route = "menuapi/feed/json"

    /**
     * externalServices:
     *  - luo mock servicen, jotta ei tarvitse tehdä oikeita pyyntöjä internettiin
     *  - hosts: mockattavat palvelut. Jos lisätään ei semma ravintoloita, tänne lisätään uusi host
     *  - routing: polku, johon mock vastaa (esim. menuapi/feed/json), ja vastauksena testidata
     *
     * application:
     *  - käynnistää oman palvelimen (module) testiympäristössä
     *  - palvelimen HttpClient käyttää testin enginea, jolloin sen kutsut menevät
     *    externalServicesin mockiin eikä oikeaan verkkoon
     */
    private fun ApplicationTestBuilder.startApp() {
        externalServices {
            hosts(host) {
                routing {
                    get(route) {
                        call.respondText(TestData.dataJson, ContentType.Application.Json)
                    }
                }
            }
        }

        val testEngine = client.engine
        application { module(testEngine) }
    }

    @Test
    fun getWeekPiato() = testApplication {
        startApp()

        val response = client.get("/piato")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(body.contains("ruoka eilen"))
        assertTrue(body.contains("ruoka tanaan"))
        assertTrue(body.contains("ruoka huomenna"))
    }

    @Test
    fun getTodayPiato() = testApplication {
        startApp()

        val response = client.get("/piato/today")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(body.contains("ruoka tanaan"))
        assertFalse(body.contains("ruoka eilen"))
        assertFalse(body.contains("ruoka huomenna"))
    }

    @Test
    fun getDatePiato() = testApplication {
        startApp()

        val date: LocalDate = TestData.today().plus(1, DateTimeUnit.DAY)
        val response = client.get("/piato/${date}")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(body.contains("ruoka huomenna"))
        assertFalse(body.contains("ruoka eilen"))
        assertFalse(body.contains("ruoka tanaan"))
    }
}