package org.example.concertbackend.common.model

import org.springframework.http.HttpStatus

enum class SuccessType(
    val status: String,
    val message: String,
) {
    QUEUE_REGISTERED(HttpStatus.CREATED.value().toString(), "대기열 등록에 성공했습니다."),
}
