package org.example.concertbackend.domain.user

interface UserRepository {
    fun findByIdOrNull(id: Long): User?
}
