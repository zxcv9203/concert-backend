package org.example.concertbackend.api.concert.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ConcertScheduleResponse(
    val id: Long,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    val date: LocalDateTime,
    val availableSeats: Int,
)
