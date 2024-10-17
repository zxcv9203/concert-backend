package org.example.concertbackend.infrastructure.persistence.wallet.repository

import org.example.concertbackend.infrastructure.persistence.wallet.entity.BalanceHistoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaBalanceHistoryRepository: JpaRepository<BalanceHistoryJpaEntity, Long> {
}