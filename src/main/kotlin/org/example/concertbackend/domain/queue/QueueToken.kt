package org.example.concertbackend.domain.queue

import java.time.LocalDateTime

class QueueToken(
    val userId: Long,
    val token: String,
    val expiresAt: LocalDateTime? = null,
    val id: Long = 0,
)
