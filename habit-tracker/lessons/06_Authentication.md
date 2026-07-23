# Module 06: The Bouncer (Auth & JWT)

## The Problem it Solves
We don't want just anyone deleting habits. We need to verify who the user is (Authentication) and what they are allowed to do.

## The Real-Life Analogy
A JWT (JSON Web Token) is like a VIP wristband at a concert. You show your ID once at the door (`/login`), and they give you a wristband. For the rest of the night, you just show the wristband instead of pulling out your ID every time you buy a drink.

## Key Setup
We used Ktor's `auth` and `auth-jwt` plugins. We wrapped our secure routes inside an `authenticate("auth-jwt") { ... }` block.
