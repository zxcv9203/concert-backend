package org.example.concertbackend.api.payment.request

import io.swagger.v3.oas.annotations.media.Schema
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType

@Schema(name = "결제 요청")
data class PaymentRequest(
    @Schema(description = "사용자 ID", example = "1")
    val userId: Long,
    @Schema(description = "예약 ID", example = "1")
    val reservationId: Long,
) {
    init {
        require(userId > 0) { throw BusinessException(ErrorType.USER_NOT_FOUND) }
        require(reservationId > 0) { throw BusinessException(ErrorType.CONCERT_RESERVATION_NOT_FOUND) }
    }
}
