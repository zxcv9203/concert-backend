package org.example.concertbackend.infrastructure.persistence.concert.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.domain.concert.ConcertSeatStatus
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity

@Entity
@Table(name = "concert_seats")
class ConcertSeatJpaEntity(
    val concertScheduleId: Long,
    val name: String,
    val price: Long,
    @Enumerated(EnumType.STRING)
    val status: ConcertSeatStatus = ConcertSeatStatus.AVAILABLE,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
