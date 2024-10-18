package org.example.concertbackend.application.queue.service

import org.example.concertbackend.common.util.IdGenerator
import org.example.concertbackend.domain.queue.QueueToken
import org.example.concertbackend.domain.queue.QueueTokenRepository
import org.springframework.stereotype.Service

@Service
class QueueTokenCommandService(
    private val idGenerator: IdGenerator,
    private val queueTokenRepository: QueueTokenRepository,
) {
    fun create(userId: Long): QueueToken {
        val token = idGenerator.generate()
        val queueToken =
            QueueToken(
                token = token,
                userId = userId,
            )

        return queueTokenRepository.save(queueToken)
    }
}
