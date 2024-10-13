package org.example.concertbackend.common.exception

import org.example.concertbackend.common.model.ErrorType

class BusinessException(
    val errorType: ErrorType,
    cause: Throwable? = null,
) : RuntimeException(
        errorType.message,
        cause,
    )
