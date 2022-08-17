package com.lchau.tkvstore.data.repository

import com.lchau.tkvstore.domain.data.KeyValueStoreRepository
import com.lchau.tkvstore.domain.data.Store
import com.lchau.tkvstore.domain.model.Transaction
import com.lchau.tkvstore.domain.error.KeyNotSetException
import com.lchau.tkvstore.domain.error.NoTransactionException
import java.util.ArrayDeque
import javax.inject.Inject

class KeyValueStoreRepositoryImpl constructor(
    private var store: Store,
    private val transactionStack: ArrayDeque<Transaction>
) : KeyValueStoreRepository {

    @Inject
    constructor(store: Store) : this(
        store,
        ArrayDeque<Transaction>()
    )

    override suspend fun set(key: String, value: String) {
        store.set(key, value)
    }

    override suspend fun get(key: String): String {
        checkKeyNotSetException(store.get(key).isNullOrEmpty())
        return store.get(key)!!
    }

    override suspend fun delete(key: String) {
        checkKeyNotSetException(store.delete(key).isNullOrEmpty())
    }

    override suspend fun count(value: String): Int {
        return store.count(value)
    }

    override suspend fun beginTransaction() {
        val transaction = Transaction(store = store.copy())
        transactionStack.push(transaction)
    }

    override suspend fun commitTransaction() {
        checkNoTransactionException(transactionStack.isEmpty())
        transactionStack.pop()
    }

    override suspend fun rollbackTransaction() {
        checkNoTransactionException(transactionStack.isEmpty())
        val transaction = transactionStack.pop()
        store = transaction.store
    }

    private fun checkNoTransactionException(check: Boolean) {
        if (check) throw NoTransactionException()
    }

    private fun checkKeyNotSetException(check: Boolean) {
        if (check) throw KeyNotSetException()
    }
}