package com.qbssystem.auth.repositories

import com.qbssystem.auth.entities.JwtRefreshToken
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface JwtRefreshTokenRepository : BaseRepository<JwtRefreshToken, String> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM jwt_refresh_token WHERE account_id = :accountId", nativeQuery = true)
    fun deleteByAccountId(accountId: Long)
}