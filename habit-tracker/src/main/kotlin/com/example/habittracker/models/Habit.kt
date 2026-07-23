package com.example.habittracker.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// 1. Serialization Model (What the API speaks)
@Serializable
data class Habit(
    val id: Int? = null,
    val name: String,
    val category: String
)

// 2. Database Table Definition (How it's stored in SQL)
object HabitsTable : IntIdTable("habits") {
    val name = varchar("name", 255)
    val category = varchar("category", 100)
}

// 3. Exposed DAO Entity (The Kotlin Object connected to the DB row)
class HabitEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HabitEntity>(HabitsTable)

    var name by HabitsTable.name
    var category by HabitsTable.category

    // Helper to convert DB entity back to JSON model
    fun toModel() = Habit(id.value, name, category)
}
