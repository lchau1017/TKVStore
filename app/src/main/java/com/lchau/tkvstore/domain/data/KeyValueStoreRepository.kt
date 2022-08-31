package com.lchau.tkvstore.domain.data

interface KeyValueStoreRepository {

    suspend fun set(key: String, value: String)

    suspend fun get(key: String): String

    suspend fun filter(key: String): List<String>

    suspend fun delete(key: String)

    suspend fun count(value: String): Int

    suspend fun beginTransaction()

    suspend fun commitTransaction()

    suspend fun rollbackTransaction()

}