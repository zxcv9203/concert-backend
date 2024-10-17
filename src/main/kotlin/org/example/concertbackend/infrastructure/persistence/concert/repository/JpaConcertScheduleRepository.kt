package org.example.concertbackend.infrastructure.persistence.concert.repository

import org.example.concertbackend.domain.concert.ConcertSchedule
import org.example.concertbackend.domain.concert.ConcertScheduleRepository
import org.example.concertbackend.infrastructure.persistence.concert.mapper.toDomain
import org.springframework.stereotype.Repository

@Repository
class JpaConcertScheduleRepository(
    private val dataJpaConcertScheduleRepository: DataJpaConcertScheduleRepository,
) : ConcertScheduleRepository {
    override fun findAllByConcertId(concertId: Long): List<ConcertSchedule> =
        dataJpaConcertScheduleRepository
            .findAllByConcertId(concertId)
            .map { it.toDomain() }

    override fun findByConcertIdAndId(
        concertId: Long,
        scheduleId: Long,
    ): ConcertSchedule? =
        dataJpaConcertScheduleRepository
            .findByConcertIdAndId(concertId, scheduleId)
            ?.toDomain()
}
