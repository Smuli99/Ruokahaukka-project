package com.ruokkis.integration

import com.ruokkis.client.RestaurantFetcher
import com.ruokkis.database.TestDatabase
import com.ruokkis.repository.RestaurantRepository
import com.ruokkis.repository.RestaurantRepositoryImpl
import com.ruokkis.service.RestaurantSyncService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RestaurantSyncIntegrationTest {
    private lateinit var database: TestDatabase
    private lateinit var repository: RestaurantRepository
    private lateinit var service: RestaurantSyncService
    private lateinit var fetcher: RestaurantFetcher

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

//    @Test
//    fun `TODO`() = runTest {
//        //TODO
//    }
}