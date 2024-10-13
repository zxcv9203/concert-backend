package org.example.concertbackend.domain.concert

class ConcertSeat(
    val name: String,
    val status: ConcertSeatStatus = ConcertSeatStatus.AVAILABLE,
    val price: Long,
    val id: Long = 0,
)
