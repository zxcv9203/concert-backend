package org.example.concertbackend.infrastructure.persistence.concert.mapper

import org.example.concertbackend.domain.concert.ConcertSchedule
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertScheduleJpaEntity

fun ConcertScheduleJpaEntity.toDomain(): ConcertSchedule =
    ConcertSchedule(
        id = id,
        concertId = concertId,
        startTime = startTime,
        endTime = endTime,
        maxSeat = maxSeat,
    )

fun ConcertSchedule.toJpaEntity(): ConcertScheduleJpaEntity =
    ConcertScheduleJpaEntity(
        id = id,
        concertId = concertId,
        startTime = startTime,
        endTime = endTime,
        maxSeat = maxSeat,
    )
