package org.example.concertbackend.infrastructure.persistence.payment.repository

import org.example.concertbackend.infrastructure.persistence.payment.entity.PaymentHistoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaPaymentHistoryRepository : JpaRepository<PaymentHistoryJpaEntity, Long>
