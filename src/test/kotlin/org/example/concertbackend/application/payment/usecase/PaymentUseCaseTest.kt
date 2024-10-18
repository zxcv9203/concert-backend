package org.example.concertbackend.application.payment.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.api.payment.request.PaymentRequest
import org.example.concertbackend.application.concert.service.ConcertReservationCommandService
import org.example.concertbackend.application.concert.service.ConcertReservationQueryService
import org.example.concertbackend.application.payment.service.PaymentCommandService
import org.example.concertbackend.application.queue.service.QueueTokenQueryService
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueToken
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class PaymentUseCaseTest {
    @InjectMockKs
    private lateinit var paymentUseCase: PaymentUseCase

    @MockK
    private lateinit var paymentCommandService: PaymentCommandService

    @MockK
    private lateinit var waitingQueueManager: WaitingQueueManager

    @MockK
    private lateinit var queueTokenQueryService: QueueTokenQueryService

    @MockK
    private lateinit var concertReservationQueryService: ConcertReservationQueryService

    @MockK
    private lateinit var concertReservationCommandService: ConcertReservationCommandService

    @Nested
    @DisplayName("결제")
    inner class Pay {
        @Test
        @DisplayName("대기열 토큰의 유저 정보와 사용자 아이디가 일치하지 않는 경우 예외 발생")
        fun userInformationMisMatched() {
            val waitingQueueToken = QueueToken(2, "token")
            val request: PaymentRequest = PaymentRequest(1, 1)

            every { waitingQueueManager.checkActiveToken("token") } just runs
            every { queueTokenQueryService.getByToken("token") } returns waitingQueueToken

            assertThatThrownBy { paymentUseCase.pay("token", request) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_INFORMATION_MISMATCH.message)
        }
    }
}
