package org.example.concertbackend.common.model

import org.springframework.http.HttpStatus

enum class ErrorType(
    val status: String,
    val message: String,
) {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "사용자를 찾을 수 없습니다."),
    WAITING_QUEUE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "대기열 토큰을 찾을 수 없습니다."),
    CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "콘서트를 찾을 수 없습니다."),
    CONCERT_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "콘서트 일정을 찾을 수 없습니다."),
    NOT_ACTIVE_TOKEN(HttpStatus.BAD_REQUEST.value().toString(), "활성화 되지 않은 토큰입니다."),
    WALLET_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "지갑을 찾을 수 없습니다."),
    ALREADY_RESERVED(HttpStatus.BAD_REQUEST.value().toString(), "이미 예약된 좌석이 포함되어 있습니다."),
    CHARGE_AMOUNT_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST.value().toString(), "충전 금액은 0 이상이어야 합니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value().toString(), "요청 값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value().toString(), "서버 오류가 발생했습니다. 관리자에게 문의해주세요."),
}
