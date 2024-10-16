package org.example.concertbackend.application.user.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.user.User
import org.example.concertbackend.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository,
) {
    fun findById(id: Long): User =
        userRepository.findByIdOrNull(id)
            ?: throw BusinessException(ErrorType.USER_NOT_FOUND)
}
