package org.example.concertbackend.infrastructure.persistence.wallet.repository

import org.example.concertbackend.domain.wallet.Wallet
import org.example.concertbackend.domain.wallet.WalletRepository
import org.example.concertbackend.infrastructure.persistence.wallet.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.wallet.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaWalletRepository(
    private val dataJpaWalletRepository: DataJpaWalletRepository,
) : WalletRepository {
    override fun findByUserIdWithLock(userId: Long): Wallet? =
        dataJpaWalletRepository
            .findByUserIdWithLock(userId)
            ?.toDomain()

    override fun update(wallet: Wallet): Wallet =
        dataJpaWalletRepository
            .save(wallet.toJpaEntity())
            .toDomain()
}
