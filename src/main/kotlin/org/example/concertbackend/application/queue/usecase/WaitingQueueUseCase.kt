package org.example.concertbackend.application.queue.usecase

import org.example.concertbackend.api.queue.request.RegisterQueueRequest
import org.example.concertbackend.api.queue.response.RegisterQueueResponse
import org.example.concertbackend.api.queue.response.TokenPositionQueueResponse
import org.example.concertbackend.application.queue.service.QueueTokenCommandService
import org.example.concertbackend.application.queue.service.QueueTokenQueryService
import org.example.concertbackend.application.queue.service.WaitingQueueCommandService
import org.example.concertbackend.application.queue.service.WaitingQueueQueryService
import org.example.concertbackend.application.user.service.UserQueryService
import org.springframework.stereotype.Component

@Component
class WaitingQueueUseCase(
    private val userQueryService: UserQueryService,
    private val waitingQueueCommandService: WaitingQueueCommandService,
    private val queueTokenCommandService: QueueTokenCommandService,
    private val queueTokenQueryService: QueueTokenQueryService,
    private val waitingQueueQueryService: WaitingQueueQueryService,
) {
    fun addToQueue(request: RegisterQueueRequest): RegisterQueueResponse {
        userQueryService.findById(request.userId)
        val waitingQueueInToken =
            queueTokenQueryService.findByUserId(request.userId)
                ?: queueTokenCommandService
                    .create(request.userId)
                    .also { waitingQueueCommandService.addToQueue(it.token) }

        return RegisterQueueResponse(waitingQueueInToken.token)
    }

    fun findQueuePosition(token: String): TokenPositionQueueResponse {
        queueTokenQueryService.getByToken(token)
        return waitingQueueQueryService.findQueuePosition(token)
    }
}
