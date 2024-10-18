package org.example.concertbackend.infrastructure.persistence.wallet.mapper

import org.example.concertbackend.domain.wallet.BalanceHistory
import org.example.concertbackend.infrastructure.persistence.wallet.entity.BalanceHistoryJpaEntity

fun BalanceHistory.toJpaEntity(): BalanceHistoryJpaEntity =
    BalanceHistoryJpaEntity(
        id = id,
        walletId = walletId,
        balance = balance,
        type = type,
    )

fun BalanceHistoryJpaEntity.toDomain(): BalanceHistory =
    BalanceHistory(
        id = id,
        walletId = walletId,
        balance = balance,
        type = type,
    )
