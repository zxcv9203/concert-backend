package org.example.concertbackend.api.concert

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.concertbackend.api.concert.request.ReservationConcertSeatRequest
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.api.concert.response.ConcertSeatResponses
import org.example.concertbackend.api.concert.response.ReservationConcertSeatResponse
import org.example.concertbackend.common.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "콘서트 API", description = "콘서트 일정, 좌석 조회 및 예약을 위한 API 입니다.")
interface ConcertApi {
    @Operation(
        summary = "콘서트 일정 조회",
        description = "콘서트 일정을 조회합니다.",
    )
    @GetMapping("/{concertId}/schedules")
    fun findConcertSchedules(
        @PathVariable concertId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<ConcertScheduleResponses>>

    @Operation(
        summary = "콘서트 좌석 조회",
        description = "콘서트 일정의 좌석을 조회합니다.",
    )
    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    fun findConcertSeats(
        @PathVariable concertId: Long,
        @PathVariable scheduleId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
    ): ResponseEntity<ApiResponse<ConcertSeatResponses>>

    @Operation(
        summary = "콘서트 좌석 예약",
        description = "콘서트 일정의 좌석을 예약합니다.",
    )
    @PostMapping("/{concertId}/schedules/{scheduleId}/seats/reservations")
    fun reserveConcertSeats(
        @PathVariable concertId: Long,
        @PathVariable scheduleId: Long,
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @RequestBody request: ReservationConcertSeatRequest,
    ): ResponseEntity<ApiResponse<ReservationConcertSeatResponse>>
}
