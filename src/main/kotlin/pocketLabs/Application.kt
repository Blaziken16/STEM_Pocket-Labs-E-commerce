package com.example.pocketLabs

import com.example.pocketLabs.database.DatabaseFactory
import com.example.pocketLabs.plugins.configureRouting
import com.example.pocketLabs.plugins.configureSecurity
import com.example.pocketLabs.plugins.configureSerialization
import io.ktor.server.application.Application


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSecurity()
    configureRouting()
    configureSerialization()
}
