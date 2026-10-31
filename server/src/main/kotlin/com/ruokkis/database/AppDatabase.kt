package com.ruokkis.database

interface AppDatabase {
    suspend fun <T> dbQuery(block: () -> T): T
}