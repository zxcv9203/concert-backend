package org.example.concertbackend.infrastructure.persistence.concert.repository

import org.example.concertbackend.domain.concert.ConcertSeatStatus
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertSeatJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaConcertSeatRepository : JpaRepository<ConcertSeatJpaEntity, Long> {
    fun countByStatusAndConcertScheduleId(
        status: ConcertSeatStatus,
        concertScheduleId: Long,
    ): Int

    fun findAllByConcertScheduleId(scheduleId: Long): List<ConcertSeatJpaEntity>
}
