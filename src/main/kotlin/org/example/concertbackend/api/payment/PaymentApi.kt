package org.example.concertbackend.api.payment

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.concertbackend.api.payment.request.PaymentRequest
import org.example.concertbackend.common.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "결제 API", description = "결제를 진행하는 API 입니다.")
interface PaymentApi {
    @Operation(
        summary = "결제 진행",
        description = "결제를 진행하는 API 입니다.",
    )
    @PostMapping
    fun pay(
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @RequestBody request: PaymentRequest,
    ): ResponseEntity<ApiResponse<Unit>>
}
