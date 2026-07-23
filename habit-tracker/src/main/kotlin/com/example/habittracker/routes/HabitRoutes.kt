package com.example.habittracker.routes

import com.example.habittracker.models.Habit
import com.example.habittracker.repository.HabitRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.habitRoutes() {
    // Inject the repository using Koin
    val repository: HabitRepository by inject()

    route("/habits") {
        get {
            call.respond(HttpStatusCode.OK, repository.getAllHabits())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val habit = repository.getHabitById(id) ?: throw NoSuchElementException("Habit not found")
            call.respond(HttpStatusCode.OK, habit)
        }

        post {
            val newHabit = call.receive<Habit>()
            if (newHabit.name.isBlank()) throw IllegalArgumentException("Name cannot be blank")
            val created = repository.createHabit(newHabit)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val updatedInfo = call.receive<Habit>()
            val updated = repository.updateHabit(id, updatedInfo) ?: throw NoSuchElementException("Habit not found")
            call.respond(HttpStatusCode.OK, updated)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val deleted = repository.deleteHabit(id)
            if (deleted) call.respondText("Habit $id deleted successfully", status = HttpStatusCode.OK)
            else throw NoSuchElementException("Habit not found")
        }
    }
}
