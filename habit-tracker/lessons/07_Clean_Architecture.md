# Module 07: The Architect (Koin & DI)

## The Problem it Solves
If we keep writing all database logic directly inside `Application.kt`, the file will become 10,000 lines long. We need separation of concerns.

## The Real-Life Analogy
Clean architecture is like a restaurant kitchen. The waiter (Routing) takes the order. The chef (Repository) cooks the food (handles the database). They don't do each other's jobs. Koin is the manager that introduces the waiter to the chef.

## Key Setup
We moved DB logic to `HabitRepository`, routing to `HabitRoutes`, and used `org.koin` to inject the repository.
