package com.lchau.tkvstore.domain.usecase

import com.lchau.tkvstore.domain.data.KeyValueStoreRepository
import com.lchau.tkvstore.domain.model.Command
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class ExecuteCommandUseCaseImpl @Inject constructor(
    private val parserUseCase: ParserUseCase,
    private val repository: KeyValueStoreRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) : ExecuteCommandUseCase {

    override suspend fun execute(input: String): Flow<Result<String?>> = flow {
        val command = parserUseCase.parse(input)
        val result = executeCommand(command)
        emit(Result.success(result))
    }.flowOn(dispatcher).catch {
        emit(Result.failure(it))
    }

    private suspend fun executeCommand(command: Command): String? {
        when (command) {
            is Command.Set -> {
                repository.set(command.key, command.value)
            }
            is Command.Count -> {
                return repository.count(command.value).toString()
            }
            is Command.Delete -> {
                repository.delete(command.key)
            }
            is Command.Get -> {
                return repository.get(command.key)
            }
            Command.Begin -> {
                repository.beginTransaction()
            }
            Command.Commit -> {
                repository.commitTransaction()
            }
            Command.Rollback -> {
                repository.rollbackTransaction()
            }
        }
        return null
    }

}
