package org.example.concertbackend.domain.wallet

class BalanceHistory(
    val walletId: Long,
    val balance: Long,
    val type: BalanceHistoryType,
    val id: Long = 0,
)
