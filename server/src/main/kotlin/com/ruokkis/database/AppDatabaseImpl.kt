package com.ruokkis.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.coroutines.CoroutineContext

/**
 * Luodaan PostgreSQL-tietokantayhteys sovellukselle
 * Lisätietoa: https://plusmobileapps.com/blog/2024/05/25/Postgres%20database%20with%20Flyway%20in%20a%20Ktor%20project/
 *
 * @param ioContext säikeet, joilla tietokanta kyselyt ajetaan (oletuksena Dispatchers.IO)
 * @param url       JDBC-osoite tietokantaan
 * @param user      tietokantakäyttäjän nimi
 * @param password  tietokantakäyttäjän salasana
 */
class AppDatabaseImpl(
    private val ioContext: CoroutineContext = Dispatchers.IO,
    url: String = System.getenv("DB_URL") ?: error("Database url is missing"),
    user: String = System.getenv("DB_USER") ?: error("Database user is missing"),
    password: String = System.getenv("DB_PASSWORD") ?: error("Database password is missing")
): AppDatabase {

    // Yhteyspooli
    private val dataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = url
            username = user
            this.password = password
            maximumPoolSize = 5
        }
    )

    init {
        Flyway.configure().dataSource(dataSource).load().migrate()
        Database.connect(dataSource)
    }

    override suspend fun <T> dbQuery(block: () -> T): T =
        withContext(ioContext) { transaction { block() } }
}
