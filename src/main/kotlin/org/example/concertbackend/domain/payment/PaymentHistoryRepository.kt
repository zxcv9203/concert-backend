package org.example.concertbackend.domain.payment

interface PaymentHistoryRepository {
    fun save(paymentHistory: PaymentHistory): PaymentHistory
}
