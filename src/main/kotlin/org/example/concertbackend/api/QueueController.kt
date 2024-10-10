package org.example.concertbackend.api

import org.example.concertbackend.api.request.RegisterQueueRequest
import org.example.concertbackend.api.response.RegisterQueueResponse
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/queue")
class QueueController {
    @PostMapping("/tokens")
    fun addToQueue(
        @RequestBody request: RegisterQueueRequest,
    ): ResponseEntity<ApiResponse<RegisterQueueResponse>> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(
                SuccessType.QUEUE_REGISTERED,
                RegisterQueueResponse(
                    token = UUID.randomUUID().toString(),
                    status = "WAITING",
                    waitingNumber = 1,
                ),
            ),
        )
}
