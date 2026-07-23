package com.example.habittracker.di

import com.example.habittracker.repository.HabitRepository
import org.koin.dsl.module

val appModule = module {
    single { HabitRepository() }
}
