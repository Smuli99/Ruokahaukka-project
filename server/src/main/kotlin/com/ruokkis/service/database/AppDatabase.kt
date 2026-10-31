package com.ruokkis.service.database

interface AppDatabase {
    suspend fun <T> dbQuery(block: () -> T): T
}