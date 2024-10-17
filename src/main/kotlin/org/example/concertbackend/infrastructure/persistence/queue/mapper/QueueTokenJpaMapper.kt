package org.example.concertbackend.infrastructure.persistence.queue.mapper

import org.example.concertbackend.domain.queue.QueueToken
import org.example.concertbackend.infrastructure.persistence.queue.entity.QueueTokenJpaEntity

fun QueueToken.toJpaEntity(): QueueTokenJpaEntity =
    QueueTokenJpaEntity(
        userId = userId,
        token = token,
        id = id,
    )

fun QueueTokenJpaEntity.toDomain(): QueueToken =
    QueueToken(
        userId = userId,
        token = token,
        id = id,
    )
