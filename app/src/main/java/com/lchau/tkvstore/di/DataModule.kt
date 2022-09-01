package com.lchau.tkvstore.di

import com.lchau.tkvstore.data.repository.KeyValueStoreRepositoryImpl
import com.lchau.tkvstore.data.memory.StoreImpl
import com.lchau.tkvstore.domain.data.KeyValueStoreRepository
import com.lchau.tkvstore.domain.data.Store
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsKeyValueStore(impl: KeyValueStoreRepositoryImpl): KeyValueStoreRepository

    @Binds
    abstract fun bindsStore(impl: StoreImpl): Store

}