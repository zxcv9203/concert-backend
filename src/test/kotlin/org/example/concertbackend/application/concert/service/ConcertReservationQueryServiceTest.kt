package org.example.concertbackend.application.concert.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ConcertReservationQueryServiceTest {
    @InjectMockKs
    private lateinit var concertReservationQueryService: ConcertReservationQueryService

    @MockK
    private lateinit var concertReservationRepository: ConcertReservationRepository

    @Nested
    @DisplayName("아이디와 현재 예약 상태로 예약 정보 조회")
    inner class GetByIdAndStatus {
        @Test
        @DisplayName("예약 정보가 존재하지 않는 경우 예외 발생")
        fun reservationNotFound() {
            val reservationId = 1L

            every { concertReservationRepository.findByIdAndStatus(reservationId, any()) } returns null

            assertThatThrownBy {
                concertReservationQueryService.getByIdAndStatus(
                    reservationId,
                    ConcertReservationStatus.PENDING,
                )
            }.isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_RESERVATION_NOT_FOUND.message)
        }
    }

    @Nested
    @DisplayName("아이디로 예약 정보 조회")
    inner class GetById {
        @Test
        @DisplayName("예약 정보가 존재하지 않는 경우 예외 발생")
        fun reservationNotFound() {
            val reservationId = 1L

            every { concertReservationRepository.findById(reservationId) } returns null

            assertThatThrownBy { concertReservationQueryService.getById(reservationId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_RESERVATION_NOT_FOUND.message)
        }
    }
}
