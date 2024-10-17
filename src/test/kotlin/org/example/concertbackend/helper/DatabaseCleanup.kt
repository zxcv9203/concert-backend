package org.example.concertbackend.helper

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Table
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@Component
class DatabaseCleanUp
    @Autowired
    constructor(
        @PersistenceContext private val entityManager: EntityManager,
    ) : InitializingBean {
        private val tables = mutableListOf<String>()

        override fun afterPropertiesSet() {
            entityManager.metamodel.entities
                .stream()
                .filter { entity -> entity.javaType.getAnnotation(Entity::class.java) != null }
                .map { entity -> entity.javaType.getAnnotation(Table::class.java).name }
                .forEach { tables.add(it) }
        }

        @Transactional
        fun execute() {
            entityManager.flush()
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
            tables.forEach { table ->
                entityManager.createNativeQuery("TRUNCATE TABLE $table").executeUpdate()
                entityManager.createNativeQuery("ALTER TABLE $table ALTER COLUMN ID RESTART WITH 1").executeUpdate()
            }
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
        }
    }
