package com.ruokkis.service

import com.ruokkis.models.NewRestaurant
import com.ruokkis.models.Restaurant
import com.ruokkis.repository.RestaurantRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID

class RestaurantServiceTest {
    private lateinit var mockRepository: RestaurantRepository
    private lateinit var service: RestaurantService2

    @BeforeEach
    fun setup() {
        mockRepository = mockk<RestaurantRepository>()
        service = RestaurantService2(mockRepository)
    }

    @Nested
    inner class GetRestaurants {
        @Test
        fun `all restaurants are returned` () = runTest {
            val restaurants = listOf(
                Restaurant(
                    id = UUID.randomUUID().toString(),
                    name = "Piato",
                    url = "www.example.com",
                    campus = "Agora"
                ),
                Restaurant(
                    id = UUID.randomUUID().toString(),
                    name = "Taide",
                    url = "www.example.com",
                    campus = "Lahde"
                )
            )

            coEvery { mockRepository.findAll() } returns restaurants

            val result = service.getRestaurants()
            assertEquals(restaurants, result)

            coVerify(exactly = 1) { mockRepository.findAll() }
        }

        @Test
        fun `returns empty list if no restaurants in database` () = runTest {
            val restaurants = emptyList<Restaurant>()
            coEvery { mockRepository.findAll() } returns restaurants

            val result = service.getRestaurants()
            assertEquals(restaurants, result)
            coVerify(exactly = 1) { mockRepository.findAll() }
        }
    }

    // Testit uuden ravintolan luontiin
    @Nested
    inner class CreateRestaurant {
        @Test
        fun `restaurant is created when it does not exist` () = runTest {
            val newRestaurant = NewRestaurant(
                name = "Test Restaurant Name",
                url = "www.example.com",
                campus = "Test Campus",
            )

            val expectedResult = Restaurant(
                id = UUID.randomUUID().toString(),
                name = "Test Restaurant Name",
                url = "www.example.com",
                campus = "Test Campus",
            )

            coEvery { mockRepository.findByName(newRestaurant.name) } returns false
            coEvery { mockRepository.create(newRestaurant) } returns expectedResult

            val result = service.createRestaurant(newRestaurant)
            assertEquals(expectedResult, result)

            coVerify(exactly = 1) { mockRepository.findByName(newRestaurant.name) }
            coVerify(exactly = 1) { mockRepository.create(newRestaurant) }
        }

        @Test
        fun `restaurant is not created when it already exists` () = runTest {
            val newRestaurant = NewRestaurant(
                name = "Already Exists",
                url = "https://example.com",
                campus = "Test Campus"
            )

            coEvery { mockRepository.findByName(newRestaurant.name) } returns true

            val result = service.createRestaurant(newRestaurant)
            assertEquals(null, result)

            coVerify(exactly = 1) { mockRepository.findByName(newRestaurant.name) }
            coVerify(exactly = 0) { mockRepository.create(newRestaurant) }
        }
    }
}