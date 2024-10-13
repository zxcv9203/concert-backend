package org.example.concertbackend.infrastructure.persistence.wallet.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.domain.wallet.BalanceHistoryType
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity

@Entity
@Table(name = "balance_history")
class BalanceHistoryJpaEntity(
    val walletId: Long,
    val balance: Long,
    @Enumerated(EnumType.STRING)
    val status: BalanceHistoryType,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
