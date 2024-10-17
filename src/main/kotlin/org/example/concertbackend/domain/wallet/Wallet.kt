package org.example.concertbackend.domain.wallet

class Wallet(
    val userId: Long,
    var balance: Long,
    val id: Long = 0,
) {
    fun charge(amount: Long) {
        this.balance += amount
    }
}
