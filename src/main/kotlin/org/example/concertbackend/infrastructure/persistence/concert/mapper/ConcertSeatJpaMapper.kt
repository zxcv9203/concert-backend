package org.example.concertbackend.infrastructure.persistence.concert.mapper

import org.example.concertbackend.domain.concert.ConcertSeat
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertSeatJpaEntity

fun ConcertSeatJpaEntity.toDomain() =
    ConcertSeat(
        id = id,
        concertScheduleId = concertScheduleId,
        name = name,
        status = status,
        price = price,
    )

fun ConcertSeat.toJpaEntity() =
    ConcertSeatJpaEntity(
        id = id,
        concertScheduleId = concertScheduleId,
        name = name,
        status = status,
        price = price,
    )
