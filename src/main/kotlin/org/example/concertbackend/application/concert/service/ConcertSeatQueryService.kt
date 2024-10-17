package org.example.concertbackend.application.concert.service

import org.example.concertbackend.domain.concert.ConcertSeatRepository
import org.springframework.stereotype.Service

@Service
class ConcertSeatQueryService(
    private val concertSeatRepository: ConcertSeatRepository,
) {
    fun countAvailableSeats(concertScheduleId: Long): Int = concertSeatRepository.countAvailableSeats(concertScheduleId)
}
