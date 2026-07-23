# Module 05: The Vault (Persistence with Exposed)

## The Problem it Solves
Our previous `habitList` was stored in memory. Every time we restarted the server, all habits vanished! To build a real application, we need a database.

## The Real-Life Analogy
Using an in-memory list is like keeping your cash in your pocket. If you change pants, the money is gone. Connecting to a database is like putting it in a bank vault.

## Key Setup
We use JetBrains Exposed as our ORM (Object-Relational Mapping). We used the DAO (Data Access Object) approach.
1. `HabitsTable` defines the SQL schema.
2. `HabitEntity` links a Kotlin object to a row in the table.
3. Every database operation must be wrapped in a `transaction { ... }` block!
