package org.example.concertbackend.infrastructure.persistence.concert.repository

import org.example.concertbackend.domain.concert.Concert
import org.example.concertbackend.domain.concert.ConcertRepository
import org.example.concertbackend.infrastructure.persistence.concert.mapper.toDomain
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class JpaConcertRepository(
    private val dataJpaConcertRepository: DataJpaConcertRepository,
) : ConcertRepository {
    override fun findById(concertId: Long): Concert? =
        dataJpaConcertRepository
            .findByIdOrNull(concertId)
            ?.toDomain()
}
