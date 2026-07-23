# Module 09: The Megaphone (WebSockets)

## The Problem it Solves
HTTP is a one-way street: the client asks, the server answers. But what if the server wants to push a live notification (like a chat message)? WebSockets keep an open two-way connection.

## Key Setup
Installed the `WebSockets` plugin and used the `webSocket("/chat") { }` route to handle `incoming` frames and `send` responses.
