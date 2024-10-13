package org.example.concertbackend.infrastructure.persistence.wallet.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity

@Entity
@Table(name = "wallets")
class WalletJpaEntity(
    val userId: Long,
    val balance: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
