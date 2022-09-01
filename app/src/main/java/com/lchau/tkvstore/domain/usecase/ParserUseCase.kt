package com.lchau.tkvstore.domain.usecase

import com.lchau.tkvstore.domain.error.UnknownCommandException
import com.lchau.tkvstore.domain.model.Command
import com.lchau.tkvstore.domain.model.Command.*

import javax.inject.Inject

private const val GET_COMMAND = "GET"
private const val SET_COMMAND = "SET"
private const val DELETE_COMMAND = "DELETE"
private const val COUNT_COMMAND = "COUNT"
private const val BEGIN_COMMAND = "BEGIN"
private const val COMMIT_COMMAND = "COMMIT"
private const val ROLLBACK_COMMAND = "ROLLBACK"

class ParserUseCase @Inject constructor() {

    fun parse(inputString: String): Command {
        val inputs = inputString.split(Regex("""\s+"""))
        if (inputs.isEmpty() || (inputs.size > 3)) {
            throw UnknownCommandException()
        }

        val command = inputs.first().uppercase()
        val args = inputs.drop(1)

        when (command) {
            BEGIN_COMMAND -> if (args.isEmpty()) return Begin
            COMMIT_COMMAND -> if (args.isEmpty()) return Commit
            ROLLBACK_COMMAND -> if (args.isEmpty()) return Rollback
            GET_COMMAND -> if (args.size == 1) return Get(args[0])
            DELETE_COMMAND -> if (args.size == 1) return Delete(args[0])
            COUNT_COMMAND -> if (args.size == 1) return Count(args[0])
            SET_COMMAND -> if (args.size == 2) return Set(args[0], args[1])
        }

        throw UnknownCommandException()
    }
}



