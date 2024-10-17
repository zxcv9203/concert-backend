package org.example.concertbackend.infrastructure.persistence.concert.repository

import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertScheduleJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaConcertScheduleRepository : JpaRepository<ConcertScheduleJpaEntity, Long> {
    fun findAllByConcertId(concertId: Long): List<ConcertScheduleJpaEntity>
}
