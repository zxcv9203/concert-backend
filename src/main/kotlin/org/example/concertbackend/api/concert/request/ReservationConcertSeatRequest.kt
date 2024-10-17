package org.example.concertbackend.api.concert.request

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType

data class ReservationConcertSeatRequest(
    val seats: List<Long>,
) {
    init {
        require(seats.isNotEmpty()) { throw BusinessException(ErrorType.INVALID_REQUEST) }
        require(seats.none { it < 0 }) { throw BusinessException(ErrorType.INVALID_REQUEST) }
    }
}
