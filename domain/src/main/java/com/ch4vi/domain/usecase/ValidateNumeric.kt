package com.ch4vi.domain.usecase

import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class ValidateNumeric : UseCase<Boolean, ValidateNumeric.Params>() {

    private val numericRegex = Regex("-?\\d+(\\.\\d+)?")

    override suspend fun run(params: Params?): Either<Failure, Boolean> {
        params ?: return onError(Failure.ParamsFailure())
        return if (params.number == null ||
            params.number.isBlank() ||
            !numericRegex.matches(params.number)
        ) Either.Success(false)
        else Either.Success(true)
    }

    private fun onError(failure: Failure) = Either.Error(failure)
    class Params(val number: String?)
}