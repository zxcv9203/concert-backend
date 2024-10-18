package org.example.concertbackend.domain.user

interface UserRepository {
    fun findById(id: Long): User?
}
