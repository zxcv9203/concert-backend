package org.example.concertbackend.infrastructure.persistence.queue.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "queue_tokens")
class QueueTokenJpaEntity(
    val userId: Long,
    val token: String,
    val expiresAt: LocalDateTime?,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
