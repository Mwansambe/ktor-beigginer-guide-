package com.example.habittracker

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable data class Habit(val id: Int? = null, val name: String, val category: String)

val habitList = mutableListOf<Habit>()
var nextId = 1

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) { json() }
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause -> call.respondText("400: \${cause.message}", status = HttpStatusCode.BadRequest) }
        exception<NoSuchElementException> { call, cause -> call.respondText("404: \${cause.message}", status = HttpStatusCode.NotFound) }
    }
    routing {
        route("/habits") {
            get { call.respond(HttpStatusCode.OK, habitList) }
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
                val habit = habitList.find { it.id == id } ?: throw NoSuchElementException("Habit not found")
                call.respond(HttpStatusCode.OK, habit)
            }
            post {
                val newHabit = call.receive<Habit>()
                if (newHabit.name.isBlank()) throw IllegalArgumentException("Name cannot be blank")
                val habitToSave = newHabit.copy(id = nextId++)
                habitList.add(habitToSave)
                call.respond(HttpStatusCode.Created, habitToSave)
            }
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
                val updatedInfo = call.receive<Habit>()
                val index = habitList.indexOfFirst { it.id == id }
                if (index == -1) throw NoSuchElementException("Habit not found")
                val savedHabit = habitList[index].copy(name = updatedInfo.name, category = updatedInfo.category)
                habitList[index] = savedHabit
                call.respond(HttpStatusCode.OK, savedHabit)
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
                if (habitList.removeIf { it.id == id }) call.respondText("Deleted", status = HttpStatusCode.OK)
                else throw NoSuchElementException("Habit not found")
            }
        }
    }
}
