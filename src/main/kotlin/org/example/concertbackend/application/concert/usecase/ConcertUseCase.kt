package org.example.concertbackend.application.concert.usecase

import org.example.concertbackend.api.concert.request.ReservationConcertSeatRequest
import org.example.concertbackend.api.concert.response.ConcertScheduleResponse
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.api.concert.response.ConcertSeatResponses
import org.example.concertbackend.api.concert.response.ReservationConcertSeatResponse
import org.example.concertbackend.application.concert.mapper.toResponse
import org.example.concertbackend.application.concert.service.ConcertQueryService
import org.example.concertbackend.application.concert.service.ConcertReservationCommandService
import org.example.concertbackend.application.concert.service.ConcertScheduleQueryService
import org.example.concertbackend.application.concert.service.ConcertSeatQueryService
import org.example.concertbackend.application.queue.service.QueueTokenQueryService
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.springframework.stereotype.Component

@Component
class ConcertUseCase(
    private val waitingQueueManager: WaitingQueueManager,
    private val queueTokenQueryService: QueueTokenQueryService,
    private val concertQueryService: ConcertQueryService,
    private val concertScheduleQueryService: ConcertScheduleQueryService,
    private val concertSeatQueryService: ConcertSeatQueryService,
    private val concertReservationCommandService: ConcertReservationCommandService,
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

    fun findSeats(
        concertId: Long,
        scheduleId: Long,
        token: String,
    ): ConcertSeatResponses {
        waitingQueueManager.checkActiveToken(token)

        concertQueryService.getById(concertId)
        concertScheduleQueryService.getByConcertIdAndId(scheduleId, concertId)

        return concertSeatQueryService
            .findAllByScheduleId(scheduleId)
            .map { it.toResponse() }
            .let { ConcertSeatResponses(it) }
    }

    fun reserveSeats(
        concertId: Long,
        scheduleId: Long,
        token: String,
        request: ReservationConcertSeatRequest,
    ): ReservationConcertSeatResponse {
        waitingQueueManager.checkActiveToken(token)

        concertQueryService.getById(concertId)
        concertScheduleQueryService.getByConcertIdAndId(scheduleId, concertId)

        val waitingQueueToken = queueTokenQueryService.getByToken(token)
        val reservation = concertReservationCommandService.reserveSeats(scheduleId, request.seats, waitingQueueToken.userId)

        return ReservationConcertSeatResponse(
            id = reservation.id,
            totalPrice = reservation.totalPrice,
        )
    }
}
