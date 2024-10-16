package org.example.concertbackend.infrastructure.persistence.queue.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "waiting_queue")
class WaitingQueueJpaEntity(
    val token: String,
    @Enumerated(EnumType.STRING)
    val status: QueueStatus,
    val expiresAt: LocalDateTime?,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
