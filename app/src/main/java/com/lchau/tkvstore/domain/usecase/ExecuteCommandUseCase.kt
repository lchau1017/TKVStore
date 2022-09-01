package com.lchau.tkvstore.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ExecuteCommandUseCase {
    suspend fun execute(input: String): Flow<Result<String?>>
}