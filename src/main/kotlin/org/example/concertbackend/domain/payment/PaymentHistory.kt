package org.example.concertbackend.domain.payment

import java.time.LocalDateTime

class PaymentHistory(
    val walletId: Long,
    val concertReservationId: Long,
    val amount: Long,
    val status: PaymentStatus,
    val payment_time: LocalDateTime,
    val id: Long = 0,
)
