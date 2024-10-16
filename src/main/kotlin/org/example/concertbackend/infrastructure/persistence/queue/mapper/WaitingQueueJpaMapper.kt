package org.example.concertbackend.infrastructure.persistence.queue.mapper

import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity

fun WaitingQueue.toJpaEntity(): WaitingQueueJpaEntity =
    WaitingQueueJpaEntity(
        id = id,
        token = token,
        status = status,
    )

fun WaitingQueueJpaEntity.toDomain(): WaitingQueue =
    WaitingQueue(
        id = id,
        token = token,
        status = status,
    )
