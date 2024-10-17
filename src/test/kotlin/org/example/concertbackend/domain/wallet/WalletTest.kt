package org.example.concertbackend.domain.wallet

import org.assertj.core.api.Assertions.assertThat
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
}
