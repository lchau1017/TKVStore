package com.lchau.tkvstore.domain.model

sealed class Command {
    data class Get(val key: String) : Command()
    data class Set(val key: String, val value: String) : Command()
    data class Delete(val key: String) : Command()
    data class Count(val value: String) : Command()
    object Begin : Command()
    object Commit : Command()
    object Rollback : Command()
}