package com.wordle.wordle.di

import com.wordle.wordle.data.repository.FakeWordsRepository
import com.wordle.wordle.data.repository.WordsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindWordsRepositoryModule(
        repositoryModuleImpl: FakeWordsRepository,
    ): WordsRepository
}