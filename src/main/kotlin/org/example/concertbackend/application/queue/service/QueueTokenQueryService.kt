package org.example.concertbackend.application.queue.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueTokenRepository
import org.springframework.stereotype.Service

@Service
class QueueTokenQueryService(
    private val queueTokenRepository: QueueTokenRepository,
) {
    fun findByUserId(userId: Long) = queueTokenRepository.findByUserId(userId)

    fun getByToken(token: String) =
        queueTokenRepository.findByToken(token) ?: throw BusinessException(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND)
}
