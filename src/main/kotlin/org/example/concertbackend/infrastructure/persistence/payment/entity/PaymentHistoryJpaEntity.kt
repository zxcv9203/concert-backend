package org.example.concertbackend.infrastructure.persistence.payment.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.domain.payment.PaymentStatus
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "payment_histories")
class PaymentHistoryJpaEntity(
    val walletId: Long,
    val concertReservationId: Long,
    val amount: Long,
    @Enumerated(EnumType.STRING)
    val status: PaymentStatus,
    val paidAt: LocalDateTime,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
