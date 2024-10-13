package org.example.concertbackend.common.util

import org.springframework.stereotype.Component
import java.util.*

@Component
class UuidGenerator : IdGenerator {
    override fun generate(): String = UUID.randomUUID().toString()
}
