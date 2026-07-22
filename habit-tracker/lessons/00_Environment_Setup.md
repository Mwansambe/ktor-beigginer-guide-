# Module 00: The Foundation (Environment Setup)

## The Problem it Solves
Before we can build an API, we need a blank canvas, a way to fetch the Ktor libraries, and a way to compile our Kotlin code into something a server can run.

## The Real-Life Analogy
Think of building an API like opening a new restaurant.
*   **The JDK (Java Development Kit)** is the plot of land and the basic utilities (water, electricity). You can't build anything without it.
*   **Gradle** is your general contractor. You give it a blueprint (the `build.gradle.kts` file) listing what materials you need (Ktor, database drivers), and Gradle goes out, buys them, and builds the structure for you.
*   **The Ktor Project** is the empty restaurant building itself, waiting for us to design the menu and hire the chefs.

## Anatomy of a Ktor Project
*   `build.gradle.kts`: Our blueprint. Defines the Kotlin version, Ktor version, and dependencies.
*   `src/main/resources/application.conf`: Ktor's configuration file. Tells the server what port to run on and which module to load.
*   `src/main/kotlin/com/example/habittracker/Application.kt`: The heart of our application. This is where we write our routing and logic.

## Key Code Snippets

```kotlin
// The Entry Point
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
```
This tells our program to start the Netty web server engine. It automatically looks for `application.conf`.

```kotlin
// The Application Module
fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
```
This is an "Extension Function" on Ktor's `Application` class where we configure our server. `call` holds the context of the current request.

## Mastery Check (Exercise)
**Question:** If we wanted to change the server port from 8080 to 3000, which file would we modify?
**Answer:** `src/main/resources/application.conf`
