package no.nav.emottak.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.ResultSet
import no.nav.emottak.Environment
import no.nav.emottak.VaultSecrets

class Database(
    private val env: Environment,
    private val vaultCredentiala: VaultSecrets
) : DatabaseInterface {

    private val dataSource: HikariDataSource

    override val connection: Connection
        get() = dataSource.connection

    init {
        dataSource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = env.databaseUrl
            username = vaultCredentiala.databaseUsername
            password = vaultCredentiala.databasePassword
            maximumPoolSize = 3
            isAutoCommit = false
            driverClassName = "oracle.jdbc.OracleDriver"
            validate()
        })
    }
}

fun <T> ResultSet.toList(mapper: ResultSet.() -> T) = mutableListOf<T>().apply {
    while (next()) {
        add(mapper())
    }
}

interface DatabaseInterface {
    val connection: Connection
}
