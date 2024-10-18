package org.example.concertbackend.infrastructure.persistence.user.repository

import org.example.concertbackend.domain.user.User
import org.example.concertbackend.domain.user.UserRepository
import org.example.concertbackend.infrastructure.persistence.user.mapper.toDomain
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class JpaUserRepository(
    private val dataJpaUserRepository: DataJpaUserRepository,
) : UserRepository {
    override fun findById(id: Long): User? = dataJpaUserRepository.findByIdOrNull(id)?.toDomain()
}
