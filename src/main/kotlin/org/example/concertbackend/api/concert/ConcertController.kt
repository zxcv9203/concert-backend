package org.example.concertbackend.api.concert

import org.example.concertbackend.api.concert.request.ReservationConcertSeatRequest
import org.example.concertbackend.api.concert.response.ConcertScheduleResponse
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.api.concert.response.ConcertSeatResponse
import org.example.concertbackend.api.concert.response.ConcertSeatResponses
import org.example.concertbackend.api.concert.response.ReservationConcertSeatResponse
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
                    SuccessType.CONCERT_SCHEDULE_FOUND,
                    ConcertScheduleResponses(concertSchedules),
                ),
            )
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    fun findConcertSeats(
        @PathVariable concertId: Long,
        @PathVariable scheduleId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<ConcertSeatResponses>> {
        val seats =
            ConcertSeatResponses(
                listOf(
                    ConcertSeatResponse(
                        id = 1,
                        seatNumber = "A1",
                        status = "AVAILABLE",
                        price = 100000,
                    ),
                    ConcertSeatResponse(
                        id = 2,
                        seatNumber = "B2",
                        status = "IN_PROGRESS",
                        price = 10000,
                    ),
                    ConcertSeatResponse(
                        id = 3,
                        seatNumber = "C3",
                        status = "RESERVED",
                        price = 1000,
                    ),
                ),
            )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    SuccessType.CONCERT_SEAT_FOUND,
                    seats,
                ),
            )
    }

    @PostMapping("/{concertId}/schedules/{scheduleId}/seats/reservations")
    fun reserveConcertSeats(
        @PathVariable concertId: Long,
        @PathVariable scheduleId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @RequestBody request: ReservationConcertSeatRequest,
    ): ResponseEntity<ApiResponse<ReservationConcertSeatResponse>> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(SuccessType.CONCERT_SEAT_RESERVED, ReservationConcertSeatResponse(1, 10000)))
}
