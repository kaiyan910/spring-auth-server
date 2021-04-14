package com.qbssystem.auth.repositories

import com.qbssystem.auth.entities.Account
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface AccountRepository : BaseRepository<Account, Long> {

    fun findByUsername(username: String): Account?
    fun existsByPhone(phone: String): Boolean

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE account a SET a.enabled = 0 WHERE a.id = :accountId",
        nativeQuery = true
    )
    fun disable(accountId: Long)
}