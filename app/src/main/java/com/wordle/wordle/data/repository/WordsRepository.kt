package com.wordle.wordle.data.repository

import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    fun getWordsByLength(length: Int): Flow<List<String>>
}