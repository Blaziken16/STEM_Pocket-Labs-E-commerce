package com.example.pocketLabs.database

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
        val jdbcUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/pocketlabs_db"
        val dbUser = System.getenv("DB_USER") ?: "postgres"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "q1!w2@e3#"

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
