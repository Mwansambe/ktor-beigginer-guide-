# Module 08: The Inspector (Testing)

## The Problem it Solves
Every time we change code, we might break something. Manually using `curl` is slow. We need automated tests.

## Key Setup
Ktor provides `testApplication { }`. It spins up a fake version of your server in memory so you can make HTTP requests to it and assert the responses without actually binding to a real port.
