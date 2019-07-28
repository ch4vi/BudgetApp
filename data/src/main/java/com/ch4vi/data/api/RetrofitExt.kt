package com.ch4vi.data.api

import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import retrofit2.Call

internal fun <T> Call<T>.call(): Either<Failure, T> {
    return try {
        val response = this.execute()
        if (response.isSuccessful) {
            response.body().let {
                it ?: return Either.Error(
                    Failure.RequestFailure("body is null")
                )
                return Either.Success(it)
            }
        } else {
            return Either.Error(
                Failure.RequestFailure("${response.code()} ${response.errorBody()?.string()}")
            )
        }
    } catch (e: Exception) {
        Either.Error(Failure.RequestFailure(e.localizedMessage))
    }
}