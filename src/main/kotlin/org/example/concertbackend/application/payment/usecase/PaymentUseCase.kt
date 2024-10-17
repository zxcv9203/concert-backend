package org.example.concertbackend.application.payment.usecase

import org.example.concertbackend.api.payment.request.PaymentRequest
import org.example.concertbackend.application.concert.service.ConcertReservationCommandService
import org.example.concertbackend.application.concert.service.ConcertReservationQueryService
import org.example.concertbackend.application.payment.service.PaymentCommandService
import org.example.concertbackend.application.queue.service.QueueTokenQueryService
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentUseCase(
    private val waitingQueueManager: WaitingQueueManager,
    private val queueTokenQueryService: QueueTokenQueryService,
    private val concertReservationQueryService: ConcertReservationQueryService,
    private val concertReservationCommandService: ConcertReservationCommandService,
    private val paymentCommandService: PaymentCommandService,
) {
    @Transactional
    fun pay(
        token: String,
        request: PaymentRequest,
    ) {
        waitingQueueManager.checkActiveToken(token)
        val waitingQueueToken = queueTokenQueryService.getByToken(token)
        if (waitingQueueToken.userId != request.userId) {
            throw BusinessException(ErrorType.USER_INFORMATION_MISMATCH)
        }

        val concertReservation =
            concertReservationQueryService.getByIdAndStatus(request.reservationId, ConcertReservationStatus.PENDING)

        paymentCommandService.pay(concertReservation.id, concertReservation.totalPrice, waitingQueueToken.userId)
        concertReservationCommandService.confirmReservation(concertReservation.id)
    }
}
