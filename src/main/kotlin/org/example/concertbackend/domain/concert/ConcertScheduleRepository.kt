package org.example.concertbackend.domain.concert

interface ConcertScheduleRepository {
    fun findAllByConcertId(concertId: Long): List<ConcertSchedule>
}
