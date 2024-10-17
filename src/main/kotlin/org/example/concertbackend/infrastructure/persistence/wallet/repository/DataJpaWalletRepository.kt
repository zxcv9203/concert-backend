package org.example.concertbackend.infrastructure.persistence.wallet.repository

import jakarta.persistence.LockModeType
import org.example.concertbackend.infrastructure.persistence.wallet.entity.WalletJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface DataJpaWalletRepository : JpaRepository<WalletJpaEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM WalletJpaEntity w WHERE w.userId = :userId")
    fun findByUserIdWithLock(userId: Long): WalletJpaEntity?
}
