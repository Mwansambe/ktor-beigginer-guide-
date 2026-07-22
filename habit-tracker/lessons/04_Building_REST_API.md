# Module 04: The Blueprint (Building a Real REST API)

## The Problem it Solves
Right now, our habits are hardcoded or fake. A real API needs to perform CRUD (Create, Read, Update, Delete) operations on real data (or at least an in-memory list for now). We also need a structured way to handle errors. If someone tries to create a habit without a name, we shouldn't just crash; we should return a clean `400 Bad Request` with an explanation.

## The Real-Life Analogy
Think of the API as a filing clerk at a doctor's office.
*   **CRUD:** The clerk can make a new file (Create), fetch a file (Read), update a phone number (Update), or shred a file (Delete).
*   **Input Validation & Error Handling:** If you hand the clerk a form where you forgot to write your name, they don't spontaneously combust (server crash). They politely hand it back and say, "Please fill in your name" (StatusPages).

## Key Setup
We'll use Ktor's `StatusPages` plugin to catch exceptions globally and turn them into nice HTTP responses. We'll also use an in-memory mutable list to store our habits temporarily.

## Mastery Check (Exercise 4)
**Problem:** Add an `id` parameter to the POST route. Wait, POST shouldn't take an ID, it generates one! The exercise is to implement the PUT route for updating an existing habit.
Create a `put("/{id}")` route. Extract the ID. If it exists in our list, update its name and category. If not, throw an `IllegalArgumentException` which our StatusPages plugin will catch.

**Solution:**
(See `Application.kt` for the full implementation of the `put` route).
