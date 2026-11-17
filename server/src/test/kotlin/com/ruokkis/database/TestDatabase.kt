package com.ruokkis.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext

/**
 * Testeissä käytettävä tietokanta joka pyörii muistissa
 */
class TestDatabase(private val ioContext: CoroutineContext = Dispatchers.IO): AppDatabase {

    /**
     * Yhteyspooli H2-tietokantaan.
     * - Nimeen lisättävä satunnainen tunniste, jotta jokaisella testillä on oma tietokantansa
     * - MODE=PostgreSQL saa H2:n ymmärtämään PostgreSQL-syntaksia paremmin.
     * - DATABASE_TO_LOWER=TRUE pitää taulujen ja sarakkeiden nimet pienellä kuten Postgresissa.
     * - DB_CLOSE_DELAY=-1 pitää tietokannan hengissä, vaikka viimeinen yhteys suljetaan.
     */
    private val dataSource = HikariDataSource(
        HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test_${UUID.randomUUID()};" +
                    "MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1"
            maximumPoolSize = 5
        }
    )
    private val flyway = Flyway.configure()
        .dataSource(dataSource)
        .cleanDisabled(false) // sallii tietokannan tyhjennyksen testien jälkeen
        .load()

    init {
        flyway.migrate()
        Database.connect(dataSource)
    }

    /**
     * Tyhjentää tietokannan ja sulkee yhteyspoolin
     * Suljettava jokaisen testin jälkeen
     */
    fun close() {
        flyway.clean()
        dataSource.close()
    }

    override suspend fun <T> dbQuery(block: () -> T): T = withContext(ioContext) {
        transaction { block() }
    }
}