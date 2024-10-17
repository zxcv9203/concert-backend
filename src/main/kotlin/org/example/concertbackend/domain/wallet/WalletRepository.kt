package org.example.concertbackend.domain.wallet

interface WalletRepository {
    fun findByUserIdWithLock(userId: Long): Wallet?

    fun update(wallet: Wallet): Wallet
}
