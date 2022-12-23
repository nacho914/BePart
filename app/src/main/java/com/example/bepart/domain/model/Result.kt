package com.example.bepart.domain.model

sealed class Result<out T, out E> {

    data class Success<out T>(val value: T) : Result<T, Nothing>()
    data class Failure<out E>(val reason: E) : Result<Nothing, E>()

    inline fun <T, T2, E> Result<T, E>.map(f: (T) -> T2): Result<T2, E> = flatMap { value -> Success(f(value)) }

    inline fun <T, T2, E> Result<T, E>.flatMap(f: (T) -> Result<T2, E>): Result<T2, E> = when (this) {
        is Success<T> -> f(value)
        is Failure<E> -> this
    }

    fun <T> Result<T, T>.get() = when (this) {
        is Success<T> -> value
        is Failure<T> -> reason
    }
}