package com.example.habittracker

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    // Ktor 2.x testing handles loading application.conf automatically if we don't call application { module() } directly
    // Let's test the route directly to avoid Double Plugin Installation from Koin/StatusPages
    @Test
    fun testUnauthenticatedAccess() = testApplication {
        val response = client.get("/habits")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}
