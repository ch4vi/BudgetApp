package com.ch4vi.domain.usecase

import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class ValidateEmail : UseCase<Boolean, ValidateEmail.Params>() {

    private val emailRegex = Regex("^[^@]+@[^@]+\\.[^@]+$")

    override suspend fun run(params: Params?): Either<Failure, Boolean> {
        params ?: return onError(Failure.ParamsFailure())
        return if (params.email == null ||
            params.email.isBlank() ||
            !emailRegex.matches(params.email)
        ) Either.Success(false)
        else Either.Success(true)
    }

    private fun onError(failure: Failure) = Either.Error(failure)
    class Params(val email: String?)
}