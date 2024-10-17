package org.example.concertbackend.application.concert.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.springframework.stereotype.Service

@Service
class ConcertReservationQueryService(
    private val concertReservationRepository: ConcertReservationRepository,
) {
    fun getByIdAndStatus(
        reservationId: Long,
        status: ConcertReservationStatus,
    ): ConcertReservation =
        concertReservationRepository.findByIdAndStatus(reservationId, status)
            ?: throw BusinessException(ErrorType.CONCERT_RESERVATION_NOT_FOUND)

    fun getById(id: Long): ConcertReservation =
        concertReservationRepository.findById(id)
            ?: throw BusinessException(ErrorType.CONCERT_RESERVATION_NOT_FOUND)
}
