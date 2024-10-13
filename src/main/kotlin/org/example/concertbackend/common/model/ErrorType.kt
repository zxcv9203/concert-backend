package org.example.concertbackend.common.model

import org.springframework.http.HttpStatus

enum class ErrorType(
    val status: String,
    val message: String,
) {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "사용자를 찾을 수 없습니다."),
    WAITING_QUEUE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value().toString(), "대기열 토큰을 찾을 수 없습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value().toString(), "요청 값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value().toString(), "서버 오류가 발생했습니다. 관리자에게 문의해주세요."),
}
