package org.example.concertbackend.infrastructure.persistence.wallet.mapper

import org.example.concertbackend.domain.wallet.Wallet
import org.example.concertbackend.infrastructure.persistence.wallet.entity.WalletJpaEntity

fun Wallet.toJpaEntity(): WalletJpaEntity =
    WalletJpaEntity(
        id = id,
        userId = userId,
        balance = balance,
    )

fun WalletJpaEntity.toDomain(): Wallet =
    Wallet(
        id = id,
        userId = userId,
        balance = balance,
    )
