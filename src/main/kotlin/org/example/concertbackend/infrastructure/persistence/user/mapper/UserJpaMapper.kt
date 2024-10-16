package org.example.concertbackend.infrastructure.persistence.user.mapper

import org.example.concertbackend.domain.user.User
import org.example.concertbackend.infrastructure.persistence.user.entity.UserJpaEntity

fun UserJpaEntity.toDomain(): User =
    User(
        id = id,
        name = name,
    )
