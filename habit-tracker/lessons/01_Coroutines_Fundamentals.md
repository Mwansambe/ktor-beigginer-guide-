# Module 01: The Engine Room (Ktor & Coroutines)

## The Problem it Solves
Traditional web servers (Thread-per-request) assign a dedicated thread to every user request. If a request needs to wait for a database, the thread sits idle. If 1,000 users hit your API, you need 1,000 threads, which uses massive amounts of memory.

## The Real-Life Analogy
*   **Traditional Web Server:** A barista takes your order and stares at the espresso machine until your coffee is done. To serve 10 people at once, you need 10 baristas.
*   **Ktor & Coroutines:** A barista takes your order, starts the machine, and immediately takes the next person's order. When your coffee is done, they hand it to you. One barista can handle dozens of customers.

Coroutines are non-blocking. When Ktor waits for a database, it "suspends" the function, freeing the thread to handle other users.

## Ktor Pipeline
Every request goes through a series of stages (like an assembly line): logging, parsing, and finally our routing block.

## Key Code Snippets

```kotlin
get("/streak") {
    // delay() pauses THIS specific request for 2 seconds,
    // but it does NOT block the server thread.
    delay(2000L)
    call.respondText("You have a 5-day streak!")
}
```

## Mastery Check (Exercise 1)
**Problem:** Write a route block `get("/badge")` that simulates a 500ms delay and responds with "Badge unlocked: Early Bird!".
**Solution:**
```kotlin
get("/badge") {
    delay(500L)
    call.respondText("Badge unlocked: Early Bird!")
}
```
