package org.example.concertbackend.application.concert.scheduler

import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.concert.ConcertSeatRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItemRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ConcertReservationScheduler(
    private val timeProvider: TimeProvider,
    private val concertReservationRepository: ConcertReservationRepository,
    private val concertReservationItemRepository: ConcertReservationItemRepository,
    private val concertSeatRepository: ConcertSeatRepository,
) {
    @Scheduled(fixedRate = 10000)
    @Transactional
    fun expireReservations() {
        val now = timeProvider.now()
        val expiredReservations = concertReservationRepository.findExpiredReservations(now)
        expiredReservations.forEach {
            it.expire()
            concertReservationRepository.update(it)
            concertReservationItemRepository.findByReservationId(it.id).forEach { item ->
                val seat = concertSeatRepository.findById(item.concertSeatId)!!
                seat.cancelReservation()
                concertSeatRepository.update(seat)
            }
        }
    }
}
