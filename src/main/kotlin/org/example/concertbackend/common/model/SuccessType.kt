package org.example.concertbackend.common.model

import org.springframework.http.HttpStatus

enum class SuccessType(
    val status: String,
    val message: String,
) {
    QUEUE_REGISTERED(HttpStatus.CREATED.value().toString(), "대기열 등록에 성공했습니다."),
    QUEUE_POSITION_FOUND(HttpStatus.OK.value().toString(), "현재 대기열 상태 조회에 성공했습니다."),

    CONCERT_SCHEDULE_FOUND(HttpStatus.OK.value().toString(), "콘서트 일정 조회에 성공했습니다."),
    CONCERT_SEAT_FOUND(HttpStatus.OK.value().toString(), "콘서트 좌석 조회에 성공했습니다."),
    CONCERT_SEAT_RESERVED(HttpStatus.OK.value().toString(), "콘서트 좌석 예약에 성공했습니다."),
}
