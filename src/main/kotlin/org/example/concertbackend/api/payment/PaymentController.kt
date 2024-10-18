package org.example.concertbackend.api.payment

import org.example.concertbackend.api.payment.request.PaymentRequest
import org.example.concertbackend.application.payment.usecase.PaymentUseCase
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/payments")
class PaymentController(
    private val paymentUseCase: PaymentUseCase,
) : PaymentApi {
    @PostMapping
    override fun pay(
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @RequestBody request: PaymentRequest,
    ): ResponseEntity<ApiResponse<Unit>> {
        paymentUseCase.pay(token, request)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.PAYMENT_SUCCESS))
    }
}
