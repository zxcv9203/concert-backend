package org.example.concertbackend.common.util

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RealTimeProvider : TimeProvider {
    override fun now(): LocalDateTime = LocalDateTime.now()
}
