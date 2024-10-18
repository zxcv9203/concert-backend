package org.example.concertbackend.application.concert.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.concert.ConcertScheduleRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ConcertScheduleQueryServiceTest {
    @InjectMockKs
    private lateinit var concertScheduleQueryService: ConcertScheduleQueryService

    @MockK
    private lateinit var concertScheduleRepository: ConcertScheduleRepository

    @Nested
    @DisplayName("콘서트 스케줄 조회")
    inner class GetByConcertIdAndId {
        @Test
        @DisplayName("전달한 콘서트 아이디와 콘서트 스케줄 ID와 매칭되는 스케줄이 없는경우 예외가 발생한다.")
        fun testGetByConcertIdAndIdNotFound() {
            val concertId = 1L
            val scheduleId = 1L

            every { concertScheduleRepository.findByConcertIdAndId(concertId, scheduleId) } returns null

            assertThatThrownBy { concertScheduleQueryService.getByConcertIdAndId(scheduleId, concertId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_SCHEDULE_NOT_FOUND.message)
        }
    }
}
