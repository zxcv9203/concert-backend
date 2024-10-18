package org.example.concertbackend.infrastructure.persistence.concert.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "concert_schedules")
class ConcertScheduleJpaEntity(
    val concertId: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val maxSeat: Int = 50,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
