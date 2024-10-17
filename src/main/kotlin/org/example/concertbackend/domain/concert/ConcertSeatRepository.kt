package org.example.concertbackend.domain.concert

interface ConcertSeatRepository {
    fun countAvailableSeats(concertScheduleId: Long): Int

    fun findAllByScheduleId(scheduleId: Long): List<ConcertSeat>

    fun findByScheduleIdAndIdsWithLock(
        scheduleId: Long,
        seatIds: List<Long>,
    ): List<ConcertSeat>

    fun update(seat: ConcertSeat): ConcertSeat
}
