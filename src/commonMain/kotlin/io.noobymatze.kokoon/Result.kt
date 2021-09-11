package io.noobymatze.kokoon

sealed class Result<out E, out A> {

    data class Success<out A>(
        val value: A
    ): Result<Nothing, A>()

    data class Failure<out E>(
        val error: E
    ): Result<E, Nothing>()

}