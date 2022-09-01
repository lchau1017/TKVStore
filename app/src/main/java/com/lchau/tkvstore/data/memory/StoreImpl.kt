package com.lchau.tkvstore.data.memory

import com.lchau.tkvstore.domain.data.Store
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class StoreImpl @Inject constructor() : Store {

    private val map = ConcurrentHashMap<String, String>()

    override suspend fun set(key: String, value: String) {
        map[key] = value
    }

    override suspend fun get(key: String): String? {
        return map[key]
    }

    override suspend fun delete(key: String): String? {
        return map.remove(key)
    }

    override suspend fun count(value: String): Int {
        return map.values.count { it == value }
    }

    override suspend fun copy(): Store {
        val store = StoreImpl()
        for (entry in map.entries) {
            store.map[entry.key] = entry.value
        }
        return store
    }
}