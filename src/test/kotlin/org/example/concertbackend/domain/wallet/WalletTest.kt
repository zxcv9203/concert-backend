package org.example.concertbackend.domain.wallet

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WalletTest {
    @Nested
    @DisplayName("충전")
    inner class Charge {
        @Test
        @DisplayName("전달한 잔액만큼 충전된다.")
        fun charge() {
            val wallet = Wallet(1, 0)

            wallet.charge(1000)

            assertThat(wallet.balance).isEqualTo(1000)
        }
    }

    @Nested
    @DisplayName("결제")
    inner class Pay {
        @Test
        @DisplayName("잔액이 부족한 경우 예외 발생")
        fun insufficientBalance() {
            val wallet = Wallet(1, 0)

            assertThatThrownBy { wallet.pay(1000) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.INSUFFICIENT_BALANCE.message)
        }

        @Test
        @DisplayName("잔액이 충분한 경우 결제가 이루어진다.")
        fun pay() {
            val wallet = Wallet(1, 1000)

            wallet.pay(1000)

            assertThat(wallet.balance).isEqualTo(0)
        }
    }
}
