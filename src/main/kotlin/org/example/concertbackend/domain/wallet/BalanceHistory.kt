package org.example.concertbackend.domain.wallet

class BalanceHistory(
    val walletId: Long,
    val amount: Long,
    val type: BalanceHistoryType,
    val id: Long = 0,
)
