package org.example.concertbackend.domain.queue

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class WaitingQueueTest {
    @Nested
    @DisplayName("대기열 토큰 활성화")
    inner class Active {
        @Test
        @DisplayName("대기열 토큰을 활성화하면 상태를 변경하고 만료 시간(5분)을 설정한다.")
        fun active() {
            val now = LocalDateTime.now()
            val waitingQueueInToken = WaitingQueue("token")

            waitingQueueInToken.active(now)

            assertThat(waitingQueueInToken.status).isEqualTo(QueueStatus.ACTIVE)
            assertThat(waitingQueueInToken.expiresAt).isEqualTo(now.plusMinutes(5))
        }
    }
}
