package org.example.concertbackend.domain.concert

interface ConcertSeatRepository {
    fun countAvailableSeats(concertScheduleId: Long): Int
}
