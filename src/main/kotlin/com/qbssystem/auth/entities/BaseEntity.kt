package com.qbssystem.auth.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : Serializable {

    companion object {
        private const val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
    @CreatedBy
    @Column(nullable = false, name = "created_by")
    var createdBy: String = "SYSTEM"
    @LastModifiedBy
    @Column(nullable = false, name = "updated_by")
    var updatedBy: String = "SYSTEM"

    @Version
    @Column(nullable = false, name ="lock_version")
    var lockVersion: Long = 0L

    override fun equals(other: Any?): Boolean {

        if (other !is BaseEntity) return false
        if (id == null || other.id == null) return false
        if (this === other) return true
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + createdBy.hashCode()
        result = 31 * result + updatedBy.hashCode()
        return result
    }
}