package org.example.concertbackend.api.concert

import org.example.concertbackend.api.concert.response.ConcertScheduleResponse
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/v1/concerts")
class ConcertController {
    @GetMapping("/{id}/schedules")
    fun findConcertSchedules(
        @PathVariable id: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<ConcertScheduleResponses>> {
        val concertSchedules =
            listOf(
                ConcertScheduleResponse(
                    id = id,
                    date = LocalDateTime.of(2024, 10, 10, 13, 0),
                    availableSeats = 10,
                ),
                ConcertScheduleResponse(
                    id = id,
                    date = LocalDateTime.of(2024, 10, 11, 13, 0),
                    availableSeats = 5,
                ),
                ConcertScheduleResponse(
                    id = id,
                    date = LocalDateTime.of(2024, 10, 12, 13, 0),
                    availableSeats = 0,
                ),
            )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    SuccessType.QUEUE_REGISTERED,
                    ConcertScheduleResponses(concertSchedules),
                ),
            )
    }
}
