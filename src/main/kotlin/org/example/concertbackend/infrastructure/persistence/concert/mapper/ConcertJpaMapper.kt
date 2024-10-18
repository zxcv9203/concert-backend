package org.example.concertbackend.infrastructure.persistence.concert.mapper

import org.example.concertbackend.domain.concert.Concert
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertJpaEntity

fun Concert.toJpaEntity(): ConcertJpaEntity =
    ConcertJpaEntity(
        id = id,
        name = name,
    )

fun ConcertJpaEntity.toDomain(): Concert =
    Concert(
        id = id,
        name = name,
    )
