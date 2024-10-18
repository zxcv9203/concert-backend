package org.example.concertbackend.domain.wallet

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType

class Wallet(
    val userId: Long,
    var balance: Long,
    val id: Long = 0,
) {
    fun charge(amount: Long) {
        this.balance += amount
    }

    fun pay(amount: Long) {
        if (this.balance < amount) {
            throw BusinessException(ErrorType.INSUFFICIENT_BALANCE)
        }
        this.balance -= amount
    }
}
