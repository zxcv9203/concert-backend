package org.example.concertbackend.infrastructure.persistence.wallet.repository

import org.example.concertbackend.domain.wallet.BalanceHistory
import org.example.concertbackend.domain.wallet.BalanceHistoryRepository
import org.example.concertbackend.infrastructure.persistence.wallet.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.wallet.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaBalanceHistoryRepository(
    private val dataJpaBalanceHistoryRepository: DataJpaBalanceHistoryRepository,
) : BalanceHistoryRepository {
    override fun save(balanceHistory: BalanceHistory): BalanceHistory =
        dataJpaBalanceHistoryRepository.save(balanceHistory.toJpaEntity()).toDomain()
}
