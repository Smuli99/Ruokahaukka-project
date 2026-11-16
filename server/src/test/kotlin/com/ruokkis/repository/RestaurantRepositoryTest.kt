package com.ruokkis.repository

import com.ruokkis.buildMenu
import com.ruokkis.database.TestDatabase
import com.ruokkis.database.tables.AllergyTable
import com.ruokkis.database.tables.DayMenuTable
import com.ruokkis.database.tables.FoodTable
import com.ruokkis.database.tables.MenuItemFoodTable
import com.ruokkis.database.tables.MenuItemTable
import com.ruokkis.database.tables.RestaurantTable
import com.ruokkis.database.tables.WeekMenuTable
import com.ruokkis.models.Restaurant
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Yksikkö testit repolle
 *
 * tänne lisätään testejä, kun repositoryyn tehdään lisää toimintoja
 */
class RestaurantRepositoryTest {
    private lateinit var database: TestDatabase
    private lateinit var repository: RestaurantRepository

    private suspend fun count(table: Table): Long =
        database.dbQuery { table.selectAll().count() }

    @BeforeEach
    fun setUp() {
        database = TestDatabase()
        repository = RestaurantRepositoryImpl(database)
    }

    @AfterEach
    fun tearDown() {
        database.close()
    }

    @Test
    fun `find all returns empty list if no restaurants in db`() = runTest {
        val result = repository.findAll()
        assertEquals(0, result.size)
    }

    @Test
    fun `new restaurant can be created`() = runTest {
        val newRestaurant = buildMenu(
            name = "Piato", url = "https://example.com/", campus = "Mattilanniemi"
        )

        val result = repository.create(newRestaurant)

        val expectedResult = Restaurant(
            id = result.id, name = "Piato", url = "https://example.com/", campus = "Mattilanniemi"
        )

        assertEquals(expectedResult, result)
        assertEquals(1, count(RestaurantTable))
    }

    @Nested                                // Kun tietokannassa dataa valmiina
    inner class InitialDataInDatabase {

        @BeforeEach
        fun setUpDb() = runTest {
            repository.create(buildMenu("Piato"))
            repository.create(buildMenu("Taide"))
            repository.create(buildMenu("Ilokivi"))
        }

        @Test
        fun `find all returns all restaurants in db`() = runTest {
            val result = repository.findAll()
            assertEquals(3, result.size)

            val names = result.map { it.name }
            assertEquals(listOf("Piato", "Taide", "Ilokivi"), names)
        }

        @Nested
        inner class FindByName {

            @Test
            fun `returns null if unknown restaurant name`() = runTest {
                assertNull(repository.findByName("Unknown"))
            }

            @Test
            fun `returns correct restaurant with known restaurant name`() = runTest {
                var result = repository.findByName("Piato")
                assertEquals("Piato", result?.name)

                result = repository.findByName("Taide")
                assertEquals("Taide", result?.name)

                result = repository.findByName("Ilokivi")
                assertEquals("Ilokivi", result?.name)
            }
        }

        @Nested
        inner class ExistsByName {

            @Test
            fun `returns false if name doesn't exist`() = runTest {
                val result = repository.existsByName("Unknown")
                assertFalse(result)
            }

            @Test
            fun `returns true if name does exist`() = runTest {
                val result = repository.existsByName("Piato")
                assertTrue(result)
            }
        }

        @Nested
        inner class CreateWeekMenu {

            @Test
            fun `week menu can be created to restaurant and found`() = runTest {
                val restaurant = assertNotNull(repository.findByName("Piato"))
                val newWeekMenu = buildMenu().weekMenu
                assertEquals(0, count(WeekMenuTable))

                val resultId = repository.createWeekMenu(restaurant.id, newWeekMenu)
                val found = assertNotNull(repository.findWeekMenu(restaurant.id, newWeekMenu.weekStart))
                assertEquals(1, count(WeekMenuTable))
                assertEquals(resultId, found.id)
            }

//            kommentin voi poistaa kun mergetty toteutus tälle
        //    @Test
//            fun `creating another week menu for a restaurant replaces old one`() = runTest {
//                val restaurant = assertNotNull(repository.findByName("Ilokivi"))
//                val weekMenu1 = buildMenu().weekMenu
//                repository.createWeekMenu(restaurant.id, weekMenu1)
//
//                val weekMenu2 = buildMenu(weekStart = LocalDate(2026, 12, 1)).weekMenu
//                val result = repository.createWeekMenu(restaurant.id, weekMenu2)
//                val found = assertNotNull(repository.findWeekMenu(restaurant.id, weekMenu2.weekStart))
//
//                assertNull(repository.findWeekMenu(restaurant.id, weekMenu1.weekStart))
//                assertEquals(1, count(WeekMenuTable))
//                assertEquals(result, found.id)
//            }

            @Test
            fun `stores days, items, foods, allergies etc`() = runTest {
                val restaurant = assertNotNull(repository.findByName("Piato"))
                val weekMenu = buildMenu().weekMenu
                repository.createWeekMenu(restaurant.id, weekMenu)

                assertEquals(2, count(DayMenuTable))
                assertEquals(2, count(MenuItemTable))
                assertEquals(4, count(MenuItemFoodTable))
                assertEquals(3, count(FoodTable))
                assertEquals(3, count(AllergyTable))
            }

            @Test
            fun `reuses same foods and allergies`() = runTest {
                val restaurant = assertNotNull(repository.findByName("Piato"))
                var weekMenu = buildMenu().weekMenu
                repository.createWeekMenu(restaurant.id, weekMenu)

                weekMenu = buildMenu(weekStart = LocalDate(2026, 9, 9)).weekMenu
                repository.createWeekMenu(restaurant.id, weekMenu)

                assertEquals(3, count(FoodTable))
                assertEquals(3, count(AllergyTable))
            }
        }
    }
}