package org.example.concertbackend.common.util

import java.time.LocalDateTime

interface TimeProvider {
    fun now(): LocalDateTime
}