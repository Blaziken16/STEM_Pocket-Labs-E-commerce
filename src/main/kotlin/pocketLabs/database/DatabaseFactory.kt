package com.example.pocketLabs.database

import com.example.pocketLabs.config.EnvConfig
import com.example.pocketLabs.database.tables.CartTable
import com.example.pocketLabs.database.tables.OrderItemTable
import com.example.pocketLabs.database.tables.OrderTable
import com.example.pocketLabs.database.tables.ProductTable
import com.example.pocketLabs.database.tables.UsersTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {

    fun init() {
        val jdbcUrl = EnvConfig.dbUrl
        val dbUser = EnvConfig.dbUser
        val dbPassword = EnvConfig.dbPassword

        Database.connect(
            url = jdbcUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                ProductTable,
                CartTable,
                OrderTable,
                OrderItemTable
            )
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}
