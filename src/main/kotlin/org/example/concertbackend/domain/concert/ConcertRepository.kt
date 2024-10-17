package org.example.concertbackend.domain.concert

interface ConcertRepository {
    fun findById(concertId: Long): Concert?
}
