package org.example.concertbackend.infrastructure.persistence.concert.repository

import jakarta.persistence.LockModeType
import org.example.concertbackend.domain.concert.ConcertSeatStatus
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertSeatJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface DataJpaConcertSeatRepository : JpaRepository<ConcertSeatJpaEntity, Long> {
    fun countByStatusAndConcertScheduleId(
        status: ConcertSeatStatus,
        concertScheduleId: Long,
    ): Int

    fun findAllByConcertScheduleId(scheduleId: Long): List<ConcertSeatJpaEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllByConcertScheduleIdAndIdInAndStatus(
        scheduleId: Long,
        seatIds: List<Long>,
        status: ConcertSeatStatus,
    ): List<ConcertSeatJpaEntity>
}
