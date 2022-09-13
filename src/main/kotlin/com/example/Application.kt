package com.example

import com.example.DataClasses.UserSession
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        configureSecurity()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
