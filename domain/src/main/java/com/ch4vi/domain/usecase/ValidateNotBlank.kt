package com.ch4vi.domain.usecase

import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class ValidateNotBlank : UseCase<Boolean, ValidateNotBlank.Params>() {

    override suspend fun run(params: Params?): Either<Failure, Boolean> {
        params ?: return onError(Failure.ParamsFailure())
        return if (params.field == null || params.field.isBlank()) Either.Success(false)
        else Either.Success(true)
    }

    private fun onError(failure: Failure) = Either.Error(failure)
    class Params(val field: String?)
}
