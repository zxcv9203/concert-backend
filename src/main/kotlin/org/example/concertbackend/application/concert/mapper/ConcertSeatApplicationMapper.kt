package org.example.concertbackend.application.concert.mapper

import org.example.concertbackend.api.concert.response.ConcertSeatResponse
import org.example.concertbackend.domain.concert.ConcertSeat

fun ConcertSeat.toResponse() =
    ConcertSeatResponse(
        id = id,
        seatNumber = name,
        status = status.name,
        price = price,
    )
