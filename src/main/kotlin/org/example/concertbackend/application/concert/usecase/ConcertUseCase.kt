package org.example.concertbackend.application.concert.usecase

import org.example.concertbackend.api.concert.response.ConcertScheduleResponse
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.application.concert.service.ConcertQueryService
import org.example.concertbackend.application.concert.service.ConcertScheduleQueryService
import org.example.concertbackend.application.concert.service.ConcertSeatQueryService
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.springframework.stereotype.Component

@Component
class ConcertUseCase(
    private val waitingQueueManager: WaitingQueueManager,
    private val concertQueryService: ConcertQueryService,
    private val concertScheduleQueryService: ConcertScheduleQueryService,
    private val concertSeatQueryService: ConcertSeatQueryService,
) {
    fun findSchedules(
        concertId: Long,
        token: String,
    ): ConcertScheduleResponses {
        waitingQueueManager.checkActiveToken(token)

        concertQueryService.getById(concertId)

        return concertScheduleQueryService
            .findAllByConcertId(concertId)
            .map { schedule ->
                ConcertScheduleResponse(
                    id = schedule.id,
                    date = schedule.startTime,
                    availableSeats = concertSeatQueryService.countAvailableSeats(schedule.id),
                )
            }.let { ConcertScheduleResponses(it) }
    }
}
