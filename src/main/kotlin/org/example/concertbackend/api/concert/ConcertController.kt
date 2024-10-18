package org.example.concertbackend.api.concert

import org.example.concertbackend.api.concert.request.ReservationConcertSeatRequest
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.api.concert.response.ConcertSeatResponses
import org.example.concertbackend.api.concert.response.ReservationConcertSeatResponse
import org.example.concertbackend.application.concert.usecase.ConcertUseCase
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/concerts")
class ConcertController(
    private val concertUseCase: ConcertUseCase,
) : ConcertApi {
    override fun findConcertSchedules(
        @PathVariable concertId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<ConcertScheduleResponses>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    SuccessType.CONCERT_SCHEDULE_FOUND,
                    concertUseCase.findSchedules(concertId, token),
                ),
            )

    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    override fun findConcertSeats(
        @PathVariable concertId: Long,
        @PathVariable scheduleId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<ConcertSeatResponses>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    SuccessType.CONCERT_SEAT_FOUND,
                    concertUseCase.findSeats(concertId, scheduleId, token),
                ),
            )

    override fun reserveConcertSeats(
        @PathVariable concertId: Long,
        @PathVariable scheduleId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @RequestBody request: ReservationConcertSeatRequest,
    ): ResponseEntity<ApiResponse<ReservationConcertSeatResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    SuccessType.CONCERT_SEAT_RESERVED,
                    concertUseCase.reserveSeats(concertId, scheduleId, token, request),
                ),
            )
}
