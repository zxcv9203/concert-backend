package org.example.concertbackend.api.queue

import org.example.concertbackend.api.queue.request.RegisterQueueRequest
import org.example.concertbackend.api.queue.response.RegisterQueueResponse
import org.example.concertbackend.api.queue.response.TokenPositionQueueResponse
import org.example.concertbackend.application.queue.usecase.WaitingQueueUseCase
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/waiting-queue")
class WaitingQueueController(
    private val waitingQueueUseCase: WaitingQueueUseCase,
) : WaitingQueueApi {
    override fun addToQueue(
        @RequestBody request: RegisterQueueRequest,
    ): ResponseEntity<ApiResponse<RegisterQueueResponse>> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(
                SuccessType.QUEUE_REGISTERED,
                waitingQueueUseCase.addToQueue(request),
            ),
        )

    override fun findQueuePosition(
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<TokenPositionQueueResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    SuccessType.QUEUE_POSITION_FOUND,
                    waitingQueueUseCase.findQueuePosition(token),
                ),
            )
}
