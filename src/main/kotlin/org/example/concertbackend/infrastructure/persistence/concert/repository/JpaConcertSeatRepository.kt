package org.example.concertbackend.infrastructure.persistence.concert.repository

import org.example.concertbackend.domain.concert.ConcertSeat
import org.example.concertbackend.domain.concert.ConcertSeatRepository
import org.example.concertbackend.domain.concert.ConcertSeatStatus
import org.example.concertbackend.infrastructure.persistence.concert.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.concert.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaConcertSeatRepository(
    private val dataJpaConcertSeatRepository: DataJpaConcertSeatRepository,
) : ConcertSeatRepository {
    override fun countAvailableSeats(concertScheduleId: Long): Int =
        dataJpaConcertSeatRepository.countByStatusAndConcertScheduleId(
            ConcertSeatStatus.AVAILABLE,
            concertScheduleId,
        )

    override fun findAllByScheduleId(scheduleId: Long): List<ConcertSeat> =
        dataJpaConcertSeatRepository
            .findAllByConcertScheduleId(scheduleId)
            .map { it.toDomain() }

    override fun findByScheduleIdAndIdsWithLock(
        scheduleId: Long,
        seatIds: List<Long>,
    ): List<ConcertSeat> =
        dataJpaConcertSeatRepository
            .findAllByConcertScheduleIdAndIdInAndStatus(scheduleId, seatIds, ConcertSeatStatus.AVAILABLE)
            .map { it.toDomain() }

    override fun update(seat: ConcertSeat): ConcertSeat =
        dataJpaConcertSeatRepository
            .save(seat.toJpaEntity())
            .toDomain()
}
