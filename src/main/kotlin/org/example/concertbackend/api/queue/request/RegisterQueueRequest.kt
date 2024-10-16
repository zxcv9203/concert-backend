package org.example.concertbackend.api.queue.request

import io.swagger.v3.oas.annotations.media.Schema
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType

@Schema(description = "대기열 등록 요청")
data class RegisterQueueRequest(
    @Schema(description = "사용자 ID", example = "1")
    val userId: Long,
) {
    init {
        require(userId > 0) { throw BusinessException(ErrorType.USER_NOT_FOUND) }
    }
}
