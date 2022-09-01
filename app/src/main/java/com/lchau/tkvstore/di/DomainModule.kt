package com.lchau.tkvstore.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.lchau.tkvstore.domain.usecase.ExecuteCommandUseCase
import com.lchau.tkvstore.domain.usecase.ExecuteCommandUseCaseImpl
import dagger.Binds

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsExecuteCommandUseCase(impl: ExecuteCommandUseCaseImpl): ExecuteCommandUseCase
}
