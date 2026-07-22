package com.example.habittracker

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
data class Habit(
    val id: Int? = null,
    val name: String,
    val category: String
)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    install(ContentNegotiation) {
        json()
    }

    routing {
        // Module 01 Exercise
        get("/badge") {
            delay(500L)
            call.respondText("Badge unlocked: Early Bird!")
        }

        route("/habits") {

            get {
                val dummyHabit = Habit(id = 1, name = "Drink Water", category = "health")
                call.respond(HttpStatusCode.OK, dummyHabit)
            }

            /*
             * MODULE 03 EXERCISE 3 SOLUTION
             * Problem: Return a 404 Not Found if the habit ID is 999.
             * Explanation: In REST APIs, the HTTP status code is just as important as the body.
             * If a client asks for a resource that doesn't exist, we must tell them 404.
             * We check the ID string. If it's "999", we use `call.respondText` with `HttpStatusCode.NotFound`.
             * Otherwise, we send back our dummy JSON object with a 200 OK.
             */
            get("/{id}") {
                val id = call.parameters["id"]
                if (id == "999") {
                    call.respondText(
                        text = "Habit not found",
                        status = HttpStatusCode.NotFound
                    )
                } else {
                    val dummyHabit = Habit(id = id?.toIntOrNull() ?: 0, name = "Dynamic Habit", category = "general")
                    call.respond(HttpStatusCode.OK, dummyHabit)
                }
            }

            post {
                val newHabit = call.receive<Habit>()
                call.respondText(
                    text = "Created habit: ${newHabit.name} in category ${newHabit.category}",
                    status = HttpStatusCode.Created
                )
            }

            // Module 02 Exercise
            delete("/{id}") {
                val id = call.parameters["id"]
                call.respondText("Habit $id deleted successfully")
            }
        }
    }
}
