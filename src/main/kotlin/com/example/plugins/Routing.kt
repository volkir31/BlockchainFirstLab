package com.example.plugins

import com.example.DataClasses.UserSession
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import io.ktor.util.*
import kotlinx.html.*
import java.util.UUID

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondHtml {
                body {
                    form { action = "/info"; method = FormMethod.post
                        label { + "Wallet Address" }
                        input { type = InputType.text; name = "address" }
                        button { type = ButtonType.submit; + "Submit"
                        }
                    }
                }
            }
        }
        post("/info") {
            val address = call.receiveParameters().getOrFail("address")
            call.sessions.set(UserSession(id = UUID.randomUUID().toString(), walletAddress = address))
            val client = HttpClient(CIO)
            val response: HttpResponse = client.get("https://api.blockcypher.com/v1/btc/test3/addrs/$address")
            call.respondText(response.bodyAsText())
        }
    }
}
