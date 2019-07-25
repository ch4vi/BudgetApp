package com.ch4vi.domain.utils

import timber.log.Timber

sealed class Failure(val message: String? = null) {
    init {
        message?.let {
            Timber.e(it)
        }
    }

    class GenericFailure(message: String?) : Failure(message)
    class MapperFailure(message: String?) : Failure(message)
    class RequestFailure(message: String?) : Failure(message)
    class ParamsFailure(message: String? = null) : Failure(message)
    class NoElementsFailure : Failure()
}