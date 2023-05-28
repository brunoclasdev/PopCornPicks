package br.com.bclas.popcornpicks.domain.util

internal sealed class Result<out A, out B> {
    data class Success<out A>(val value: A) : Result<A, Nothing>()
    object Loading : Result<Nothing, Nothing>()
    data class Failure(val throwable: Throwable? = null) : Result<Nothing, Nothing>()
    data class Error<out B>(val value: B) : Result<Nothing, B>()
}
