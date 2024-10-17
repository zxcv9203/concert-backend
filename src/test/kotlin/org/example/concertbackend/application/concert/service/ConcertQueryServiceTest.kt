package org.example.concertbackend.application.concert.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.concert.ConcertRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ConcertQueryServiceTest {
    @InjectMockKs
    private lateinit var concertQueryService: ConcertQueryService

    @MockK
    private lateinit var concertRepository: ConcertRepository

    @Nested
    @DisplayName("ID로 콘서트 조회")
    inner class GetById {
        @Test
        @DisplayName("콘서트가 존재하지 않는 경우 예외를 발생시킵니다.")
        fun notExistConcert() {
            val concertId = 1L
            every { concertRepository.findById(concertId) } returns null

            assertThatThrownBy { concertQueryService.getById(concertId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_NOT_FOUND.message)
        }
    }
}
