package org.example.concertbackend.infrastructure.persistence.payment.mapper

import org.example.concertbackend.domain.payment.PaymentHistory
import org.example.concertbackend.infrastructure.persistence.payment.entity.PaymentHistoryJpaEntity

fun PaymentHistoryJpaEntity.toDomain(): PaymentHistory =
    PaymentHistory(
        walletId = walletId,
        concertReservationId = concertReservationId,
        amount = amount,
        status = status,
        paidAt = paidAt,
        id = id,
    )

fun PaymentHistory.toJpaEntity(): PaymentHistoryJpaEntity =
    PaymentHistoryJpaEntity(
        walletId = walletId,
        concertReservationId = concertReservationId,
        amount = amount,
        status = status,
        paidAt = paidAt,
        id = id,
    )
