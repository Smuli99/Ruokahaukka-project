package com.ruokkis.service

import com.ruokkis.client.RestaurantFetcher
import com.ruokkis.config.RestaurantSource
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.NewWeekMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.models.WeekMenu
import com.ruokkis.repository.RestaurantRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class RestaurantSyncServiceTest {
    private lateinit var fetcher: RestaurantFetcher
    private lateinit var service: RestaurantSyncService
    private lateinit var repository: RestaurantRepository

    private val source = RestaurantSource(name = "ilokivi", url = "https://example.com/feed")

    private val menu = NewRestaurantMenu(
        name = "Ilokivi",
        url = "https://www.semma.fi/ravintolat/seminaarinmaki/ilokivi/",
        campus = "Seminaarinmäki",
        weekMenu = NewWeekMenu(weekStart = LocalDate(2026, 11, 2)),
    )

    private val restaurant = Restaurant(
        id = UUID.randomUUID().toString(),
        name = "Ilokivi",
        url = menu.url,
        campus = menu.campus,
    )

    @BeforeEach
    fun setUp() {
        fetcher = mockk()
        repository = mockk()
        service = RestaurantSyncService(fetcher, repository)
        coEvery { fetcher.fetch(source) } returns menu
    }

    @Test
    fun `creates restaurant and week menu when neither exists`() = runTest {
        coEvery { repository.findByName("Ilokivi") } returns null
        coEvery { repository.create(any<NewRestaurantMenu>()) } returns restaurant
        coEvery { repository.findWeekMenu(restaurant.id, menu.weekMenu.weekStart) } returns null
        coEvery { repository.createWeekMenu(restaurant.id, menu.weekMenu) } returns "week-id"

        val result = service.syncRestaurantMenu(source)

        assertEquals(restaurant, result.restaurant)
        assertEquals(true, result.weekCreated)
        coVerify(exactly = 1) { repository.create(any<NewRestaurantMenu>()) }
        coVerify(exactly = 1) { repository.createWeekMenu(restaurant.id, menu.weekMenu) }
    }

    @Test
    fun `uses existing restaurant and does not create a new one`() = runTest {
        coEvery { repository.findByName("Ilokivi") } returns restaurant
        coEvery { repository.findWeekMenu(restaurant.id, menu.weekMenu.weekStart) } returns null
        coEvery { repository.createWeekMenu(restaurant.id, menu.weekMenu) } returns "week-id"

        val result = service.syncRestaurantMenu(source)

        assertEquals(true, result.weekCreated)
        coVerify(exactly = 0) { repository.create(any<NewRestaurantMenu>()) }
    }

    @Test
    fun `does nothing when week is already synced`() = runTest {
        coEvery { repository.findByName("Ilokivi") } returns restaurant
        coEvery { repository.findWeekMenu(restaurant.id, menu.weekMenu.weekStart) } returns
                WeekMenu(id = "existingId", weekStart = menu.weekMenu.weekStart)

        val result = service.syncRestaurantMenu(source)

        assertEquals(false, result.weekCreated)
        coVerify(exactly = 0) { repository.createWeekMenu(restaurant.id, menu.weekMenu) }
    }
}