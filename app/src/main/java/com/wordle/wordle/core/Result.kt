package com.wordle.wordle.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: String) : Result<Nothing>
    data object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
    .onStart { emit(Result.Loading) }
    .catch { e ->
        val message = when (e) {
            is IOException -> "네트워크 오류가 발생했어요."
            is HttpException -> {
                try {
                    val errorJson = e.response()?.errorBody()?.string()
                    val errorObj = JSONObject(errorJson ?: "")
                    errorObj.getString("message")
                } catch (_: Exception) {
                    "알 수 없는 오류가 발생했어요."
                }
            }
            else -> e.message ?: "알 수 없는 오류"
        }
        emit(Result.Error(message))
    }