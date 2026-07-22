# Module 02: The Map (Routing & Parameters)

## The Problem it Solves
A real API needs dynamic URLs. We don't want to write a separate route for Habit #1, Habit #2, etc. We also need to organize our routes so our code doesn't become one giant, unreadable file.

## The Real-Life Analogy
Routing is like a directory in an office building.
*   **Path Parameters (`/habits/42`):** Go to the 4th floor (Habits) and ask for room 42. Fixed structure.
*   **Query Parameters (`/habits?category=health`):** Go to the Habits department and ask the receptionist to filter the files by "health".
*   **Grouping Routes:** Putting all HR offices in one hallway.

## Key Code Snippets

```kotlin
route("/habits") { // Grouping

    get {
        // Query Parameter extraction
        val category = call.request.queryParameters["category"]
        if (category != null) {
            call.respondText("Listing all habits in category: $category")
        } else {
            call.respondText("Listing all habits")
        }
    }

    get("/{id}") {
        // Path Parameter extraction
        val id = call.parameters["id"]
        call.respondText("Showing details for habit ID: $id")
    }
}
```

## Mastery Check (Exercise 2)
**Problem:** Write a route block to delete a specific habit by listening for a `DELETE` request at `/habits/{id}`. Respond with "Habit [id] deleted successfully".
**Solution:**
```kotlin
delete("/{id}") {
    val id = call.parameters["id"]
    call.respondText("Habit $id deleted successfully")
}
```
