package org.example.concertbackend.infrastructure.persistence.user.repository

import org.example.concertbackend.infrastructure.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaUserRepository : JpaRepository<UserJpaEntity, Long>
