package org.example.concertbackend.application.user.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.user.UserRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserQueryServiceTest {
    @InjectMockKs
    private lateinit var userQueryService: UserQueryService

    @MockK
    private lateinit var userRepository: UserRepository

    @Nested
    @DisplayName("id를 사용해서 유저 조회")
    inner class FindById {
        @Test
        @DisplayName("유저가 존재하지 않는 경우 예외가 발생합니다.")
        fun userNotExists() {
            val userId = 1L
            every { userRepository.findById(userId) } returns null

            assertThatThrownBy { userQueryService.getById(userId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_NOT_FOUND.message)
        }
    }
}
