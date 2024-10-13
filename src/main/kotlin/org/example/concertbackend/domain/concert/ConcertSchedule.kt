package org.example.concertbackend.domain.concert

import java.time.LocalDateTime

class ConcertSchedule(
    val concertId: Long,
    val maxSeat: Int = 50,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val id: Long = 0,
)
