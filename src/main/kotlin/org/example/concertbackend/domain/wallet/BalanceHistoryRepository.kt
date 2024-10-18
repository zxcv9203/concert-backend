package org.example.concertbackend.domain.wallet

interface BalanceHistoryRepository {
    fun save(balanceHistory: BalanceHistory): BalanceHistory
}
