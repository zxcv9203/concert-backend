package org.example.concertbackend.api.wallet.request

import io.swagger.v3.oas.annotations.media.Schema
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType

@Schema(description = "잔액 충전 요청")
data class ChargeBalanceRequest(
    @Schema(description = "충전할 금액", example = "10000")
    val amount: Long,
) {
    init {
        require(amount > 0) { throw BusinessException(ErrorType.CHARGE_AMOUNT_MUST_BE_POSITIVE) }
    }
}
