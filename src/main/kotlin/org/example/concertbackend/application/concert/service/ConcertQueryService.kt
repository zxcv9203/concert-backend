package org.example.concertbackend.application.concert.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.concert.Concert
import org.example.concertbackend.domain.concert.ConcertRepository
import org.springframework.stereotype.Service

@Service
class ConcertQueryService(
    private val concertRepository: ConcertRepository,
) {
    fun getById(concertId: Long): Concert =
        concertRepository.findById(concertId)
            ?: throw BusinessException(ErrorType.CONCERT_NOT_FOUND)
}
