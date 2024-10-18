package org.example.concertbackend.api.queue

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.concertbackend.api.queue.request.RegisterQueueRequest
import org.example.concertbackend.api.queue.response.RegisterQueueResponse
import org.example.concertbackend.api.queue.response.TokenPositionQueueResponse
import org.example.concertbackend.common.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "대기열 API", description = "대기열 등록 및 상태를 확인하기 위한 API 입니다.")
interface WaitingQueueApi {
    @Operation(
        summary = "대기열 등록",
        description = "대기열에 등록하고 토큰을 반환합니다.",
    )
    @PostMapping
    fun addToQueue(
        @RequestBody request: RegisterQueueRequest,
    ): ResponseEntity<ApiResponse<RegisterQueueResponse>>

    @Operation(
        summary = "대기열 상태 조회",
        description = "대기열에 대한 상태를 조회합니다.",
    )
    @GetMapping
    fun findQueuePosition(
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<TokenPositionQueueResponse>>
}
