package org.example.concertbackend.application.concert.service

import org.example.concertbackend.domain.concert.ConcertSchedule
import org.example.concertbackend.domain.concert.ConcertScheduleRepository
import org.springframework.stereotype.Service

@Service
class ConcertScheduleQueryService(
    private val concertScheduleRepository: ConcertScheduleRepository,
) {
    fun findAllByConcertId(concertId: Long): List<ConcertSchedule> = concertScheduleRepository.findAllByConcertId(concertId)
}
