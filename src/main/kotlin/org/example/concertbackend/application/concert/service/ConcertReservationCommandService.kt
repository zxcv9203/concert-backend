package org.example.concertbackend.application.concert.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.concert.ConcertSeatRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItem
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItemRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConcertReservationCommandService(
    private val concertReservationQueryService: ConcertReservationQueryService,
    private val timeProvider: TimeProvider,
    private val concertSeatRepository: ConcertSeatRepository,
    private val concertReservationRepository: ConcertReservationRepository,
    private val concertReservationItemRepository: ConcertReservationItemRepository,
) {
    @Transactional
    fun reserveSeats(
        scheduleId: Long,
        seatIds: List<Long>,
        userId: Long,
    ): ConcertReservation {
        val seats = concertSeatRepository.findByScheduleIdAndIdsWithLock(scheduleId, seatIds)
        if (seatIds.size != seats.size) {
            throw BusinessException(ErrorType.ALREADY_RESERVED)
        }
        val reservation =
            concertReservationRepository.save(
                ConcertReservation(
                    userId = userId,
                    totalPrice = seats.sumOf { it.price },
                    expiresAt = timeProvider.now().plusMinutes(5),
                ),
            )

        seats.forEach {
            it.reserve()
            concertReservationItemRepository.save(ConcertReservationItem(it.id, reservation.id))
            concertSeatRepository.update(it)
        }

        return reservation
    }

    fun confirmReservation(id: Long) {
        val reservation = concertReservationQueryService.getById(id)
        reservation.confirm()
        concertReservationRepository.update(reservation)
        concertReservationItemRepository.findByReservationId(id).forEach { item ->
            val seat = concertSeatRepository.findById(item.concertSeatId)!!
            seat.confirmReservation()
            concertSeatRepository.update(seat)
        }
    }
}
