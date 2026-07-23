# Module 11: The Launch (Docker)

## The Problem it Solves
"It works on my machine!" doesn't cut it. We need a way to package our Ktor app so it runs identically on an AWS server, a DigitalOcean droplet, or a coworker's Mac.

## Key Setup
We added the Gradle Shadow plugin to build a "Fat JAR" (a single file containing our code AND all Ktor dependencies). Then we wrote a `Dockerfile` to run that JAR inside a lightweight Linux container.
