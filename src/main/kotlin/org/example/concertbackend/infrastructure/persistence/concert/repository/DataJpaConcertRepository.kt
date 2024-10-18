package org.example.concertbackend.infrastructure.persistence.concert.repository

import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaConcertRepository : JpaRepository<ConcertJpaEntity, Long>
