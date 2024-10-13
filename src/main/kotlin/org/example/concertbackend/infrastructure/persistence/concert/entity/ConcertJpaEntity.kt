package org.example.concertbackend.infrastructure.persistence.concert.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity

@Entity
@Table(name = "concerts")
class ConcertJpaEntity(
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
