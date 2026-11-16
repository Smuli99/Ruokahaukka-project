package com.ruokkis.integration

import com.ruokkis.buildMenu
import com.ruokkis.client.RestaurantFetcher
import com.ruokkis.config.RestaurantSource
import com.ruokkis.database.TestDatabase
import com.ruokkis.database.tables.AllergyTable
import com.ruokkis.database.tables.DayMenuTable
import com.ruokkis.database.tables.FoodTable
import com.ruokkis.database.tables.MenuItemFoodTable
import com.ruokkis.database.tables.RestaurantTable
import com.ruokkis.database.tables.WeekMenuTable
import com.ruokkis.repository.RestaurantRepository
import com.ruokkis.repository.RestaurantRepositoryImpl
import com.ruokkis.service.RestaurantSyncService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RestaurantSyncIntegrationTest {
    private lateinit var database: TestDatabase
    private lateinit var repository: RestaurantRepository
    private lateinit var service: RestaurantSyncService
    private lateinit var fetcher: RestaurantFetcher

    private val source = RestaurantSource(name = "ilokivi", url = "https://example.com/feed")

    // Rivien määrä taulussa
    private suspend fun count(table: Table): Long =
        database.dbQuery { table.selectAll().count() }

    @BeforeEach
    fun setUp() {
        database = TestDatabase()
        repository = RestaurantRepositoryImpl(database)
        fetcher = mockk()
        service = RestaurantSyncService(fetcher, repository)
    }

    @AfterEach
    fun tearDown() {
        database.close()
    }

    @Test
    fun `first sync creates restaurant and week`() = runTest {
        coEvery { fetcher.fetch(source) } returns buildMenu()

        val result = service.syncRestaurantMenu(source)

        assertEquals(true, result.weekCreated)
        assertEquals("Ilokivi", result.restaurant.name)
        assertNotNull(repository.findByName("ilokivi"))
        assertNotNull(repository.findWeekMenu(result.restaurant.id, LocalDate(2026, 11, 2)))
    }

    @Test
    fun `syncing the same week twice does not create dublicates`() = runTest {
        coEvery { fetcher.fetch(source) } returns buildMenu()

        service.syncRestaurantMenu(source)
        val second = service.syncRestaurantMenu(source)

        assertEquals(false, second.weekCreated)
        assertEquals(1, count(RestaurantTable))
        assertEquals(1, count(WeekMenuTable))
        assertEquals(2, count(DayMenuTable))
    }

    @Test
    fun `same food on several days is only stored once`() = runTest {
        coEvery { fetcher.fetch(source) } returns buildMenu()

        service.syncRestaurantMenu(source)

        assertEquals(3, count(FoodTable))           // Riisiä jaettu molempien päivien kesken
        assertEquals(4, count(MenuItemFoodTable))   // mutta esiintymiä on 4
        assertEquals(3, count(AllergyTable))        // L, G, Veg kerran kukin
    }

    @Test
    fun `next week for an existing restaurant reuses restaurant and foods`() = runTest {
        coEvery { fetcher.fetch(source) } returns buildMenu(weekStart = LocalDate(2026, 11, 2))
        service.syncRestaurantMenu(source)

        coEvery { fetcher.fetch(source) } returns buildMenu(weekStart = LocalDate(2026, 11, 9))
        val result = service.syncRestaurantMenu(source)

        assertEquals(true, result.weekCreated)
        assertEquals(1, count(RestaurantTable))
        assertEquals(2, count(WeekMenuTable))
        assertEquals(3, count(FoodTable))           // samat ruoat, ei uusia rivejä
    }

    @Test
    fun `failed week save leaves no partial data`() = runTest {
        // Kaksi päivää samalla päivämäärällä rikkoo UNIQUE (week_menu_id, date) -rajoitteen
        val broken = buildMenu().let { menu ->
            menu.copy(
                weekMenu = menu.weekMenu.copy(
                    days = listOf(menu.weekMenu.days[0], menu.weekMenu.days[0])
                )
            )
        }
        coEvery { fetcher.fetch(source) } returns broken

        assertThrows<Exception> { service.syncRestaurantMenu(source) }

        // Transaktio peruttiin: viikosta ei jäänyt mitään
        assertEquals(0, count(WeekMenuTable))
        assertEquals(0, count(DayMenuTable))
        assertEquals(0, count(FoodTable))
    }
}