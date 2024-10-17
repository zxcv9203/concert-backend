package org.example.concertbackend.domain.concert

class ConcertSeat(
    val concertScheduleId: Long,
    val name: String,
    val price: Long,
    var status: ConcertSeatStatus = ConcertSeatStatus.AVAILABLE,
    val id: Long = 0,
) {
    fun reserve() {
        this.status = ConcertSeatStatus.RESERVED_TEMPORARY
    }

    fun cancelReservation() {
        this.status = ConcertSeatStatus.AVAILABLE
    }
}
