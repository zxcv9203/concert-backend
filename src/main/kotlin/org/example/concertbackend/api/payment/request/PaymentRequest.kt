package org.example.concertbackend.api.payment.request

data class PaymentRequest(
    val userId: Long,
    val reservationId: Long,
)
