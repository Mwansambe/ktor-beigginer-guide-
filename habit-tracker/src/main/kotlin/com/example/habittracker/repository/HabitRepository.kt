package com.example.habittracker.repository

import com.example.habittracker.models.*
import org.jetbrains.exposed.sql.transactions.transaction

class HabitRepository {
    fun getAllHabits(): List<Habit> = transaction {
        HabitEntity.all().map { it.toModel() }
    }

    fun getHabitById(id: Int): Habit? = transaction {
        HabitEntity.findById(id)?.toModel()
    }

    fun createHabit(habit: Habit): Habit = transaction {
        HabitEntity.new {
            name = habit.name
            category = habit.category
        }.toModel()
    }

    fun updateHabit(id: Int, updatedInfo: Habit): Habit? = transaction {
        val entity = HabitEntity.findById(id) ?: return@transaction null
        entity.name = updatedInfo.name
        entity.category = updatedInfo.category
        entity.toModel()
    }

    fun deleteHabit(id: Int): Boolean = transaction {
        val entity = HabitEntity.findById(id) ?: return@transaction false
        entity.delete()
        true
    }
}
