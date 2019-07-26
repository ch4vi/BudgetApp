package com.ch4vi.domain.utils

sealed class Either<out L, out R> {
    data class Error<out L>(val a: L) : Either<L, Nothing>()
    data class Success<out R>(val b: R) : Either<Nothing, R>()

    val isSuccess get() = this is Success<R>
    val isError get() = this is Error<L>

    fun <L> error(a: L) = Error(a)
    fun <R> success(b: R) = Success(b)

    fun <T> either(error: (L) -> T, success: (R) -> T): T =
        when (this) {
            is Error -> error(a)
            is Success -> success(b)
        }

    fun getSuccess(): R? = when (this) {
        is Error -> null
        is Success -> b
    }

    fun getError(): L? = when (this) {
        is Error -> a
        is Success -> null
    }
}

fun <L, R> List<Either<L, R>>.flat(): Either<L,List<R>> {
    val list = mutableListOf<R>()
    this.takeWhile {
        when (it) {
            is Either.Error -> {
                return it
            }
            is Either.Success -> {
                list.add(it.b)
                true
            }
        }
    }
    return Either.Success(list)
}

fun <L, R> List<Either<L, R>>.takeSuccess(): List<R> {
    val list = mutableListOf<R>()
    this.takeWhile {
        when (it) {
            is Either.Error -> {
                // do nothing
                true
            }
            is Either.Success -> {
                list.add(it.b)
                true
            }
        }
    }
    return list
}