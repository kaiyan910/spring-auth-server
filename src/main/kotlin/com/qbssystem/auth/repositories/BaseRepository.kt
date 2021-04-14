package com.qbssystem.auth.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>