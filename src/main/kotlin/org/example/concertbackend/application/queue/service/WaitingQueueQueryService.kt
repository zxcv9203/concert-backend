package org.example.concertbackend.application.queue.service

import org.example.concertbackend.api.queue.response.TokenPositionQueueResponse
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.springframework.stereotype.Service

@Service
class WaitingQueueQueryService(
    private val waitingQueueManager: WaitingQueueManager,
) {
    fun findQueuePosition(token: String): TokenPositionQueueResponse {
        val waitingQueueInToken =
            waitingQueueManager.findByToken(token)
                ?: throw BusinessException(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND)

        val position =
            when (waitingQueueInToken.status) {
                QueueStatus.WAITING -> waitingQueueManager.findPosition(token)
                else -> 0
            }

        return TokenPositionQueueResponse(
            token = token,
            status = waitingQueueInToken.status,
            waitingNumber = position,
        )
    }
}
