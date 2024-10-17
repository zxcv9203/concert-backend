package org.example.concertbackend.domain.concert.reservation

interface ConcertReservationItemRepository {
    fun save(concertReservationItem: ConcertReservationItem): ConcertReservationItem

    fun findByReservationId(id: Long): List<ConcertReservationItem>
}
