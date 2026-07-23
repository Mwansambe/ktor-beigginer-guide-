package com.example.habittracker

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.habittracker.di.appModule
import com.example.habittracker.models.HabitsTable
import com.example.habittracker.routes.habitRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.time.Duration
import kotlinx.coroutines.channels.consumeEach
import kotlin.time.Duration.Companion.seconds

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
    transaction { SchemaUtils.create(HabitsTable) }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    install(ContentNegotiation) { json() }

    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause -> call.respondText("400: ${cause.message}", status = HttpStatusCode.BadRequest) }
        exception<NoSuchElementException> { call, cause -> call.respondText("404: ${cause.message}", status = HttpStatusCode.NotFound) }
    }

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    // MOD 10: Production Concerns - CORS
    install(CORS) {
        anyHost() // In production, replace with specific origins
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
    }

    // MOD 10: Production Concerns - Rate Limiting
    install(RateLimit) {
        global {
            rateLimiter(limit = 100, refillPeriod = 60.seconds)
        }
    }

    val secret = "my-super-secret-key"
    val issuer = "http://localhost:8080/"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Access to Habits"
            verifier(JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build())
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") JWTPrincipal(credential.payload) else null
            }
        }
    }

    routing {

        // MOD 10: Production Concerns - OpenAPI/Swagger documentation generated on the fly!
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        post("/login") {
            val token = JWT.create().withIssuer(issuer).withClaim("username", "testuser").sign(Algorithm.HMAC256(secret))
            call.respond(hashMapOf("token" to token))
        }

        authenticate("auth-jwt") {
            habitRoutes()
        }

        webSocket("/chat") {
            send("Welcome to the Habit Tracker Chat!")
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    send("You said: ${frame.readText()}")
                }
            }
        }
    }
}
