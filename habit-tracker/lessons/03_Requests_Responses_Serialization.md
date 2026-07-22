# Module 03: The Conversation (Serialization)

## The Problem it Solves
APIs need to exchange structured data, usually JSON. We need an automatic way to convert Kotlin Data Classes into JSON, and JSON back into Kotlin Objects.

## The Real-Life Analogy
*   Your Kotlin Data Class is a document in English (native language).
*   JSON is Esperanto (universal language).
*   **Serialization:** Translating English to Esperanto before sending it.
*   **Deserialization:** Receiving an Esperanto document and translating it back to English.
*   **Content Negotiation:** The receptionist asking, "Which language do you prefer?"

## Key Setup
We use `ContentNegotiation` and `kotlinx.serialization`.
1.  Annotate data classes with `@Serializable`.
2.  Install `ContentNegotiation` in the Ktor module.

## Key Code Snippets

```kotlin
@Serializable
data class Habit(val id: Int? = null, val name: String, val category: String)

fun Application.module() {
    install(ContentNegotiation) { json() }

    routing {
        get("/habits") {
            val dummyHabit = Habit(id = 1, name = "Drink Water", category = "health")
            call.respond(HttpStatusCode.OK, dummyHabit) // Serializes to JSON
        }

        post("/habits") {
            val newHabit = call.receive<Habit>() // Deserializes from JSON
            call.respondText("Created!", status = HttpStatusCode.Created)
        }
    }
}
```

## Mastery Check (Exercise 3)
**Problem:** Write a `get("/{id}")` block. If the id is "999", return "Habit not found" and `404 Not Found`. Otherwise, return dummy JSON and `200 OK`.
**Solution:**
```kotlin
get("/{id}") {
    val id = call.parameters["id"]
    if (id == "999") {
        call.respondText("Habit not found", status = HttpStatusCode.NotFound)
    } else {
        val dummyHabit = Habit(id = id?.toIntOrNull() ?: 0, name = "Dynamic Habit", category = "general")
        call.respond(HttpStatusCode.OK, dummyHabit)
    }
}
```
