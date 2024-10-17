package org.example.concertbackend.infrastructure.persistence.payment.repository

import org.example.concertbackend.domain.payment.PaymentHistory
import org.example.concertbackend.domain.payment.PaymentHistoryRepository
import org.example.concertbackend.infrastructure.persistence.payment.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.payment.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaPaymentHistoryRepository(
    private val dataJpaPaymentHistoryRepository: DataJpaPaymentHistoryRepository,
) : PaymentHistoryRepository {
    override fun save(paymentHistory: PaymentHistory): PaymentHistory =
        dataJpaPaymentHistoryRepository
            .save(paymentHistory.toJpaEntity())
            .toDomain()
}
