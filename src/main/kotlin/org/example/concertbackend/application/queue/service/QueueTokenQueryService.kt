package org.example.concertbackend.application.queue.service

import org.example.concertbackend.domain.queue.QueueTokenRepository
import org.springframework.stereotype.Service

@Service
class QueueTokenQueryService(
    private val queueTokenRepository: QueueTokenRepository,
) {
    fun findByUserIdOrNull(userId: Long) = queueTokenRepository.findByUserIdOrNull(userId)
}
