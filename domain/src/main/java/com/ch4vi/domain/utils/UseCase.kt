package com.ch4vi.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any? {
    abstract suspend fun run(params: Params? = null): Either<Failure, Type>

    @JvmOverloads
    operator fun invoke(
        error: (Failure) -> Unit = {},
        params: Params? = null,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        success: (Type) -> Unit = {}
    ) {
        this.invoke(params, dispatcher) { result ->
            result.either({
                error(it)
            }, {
                success(it)
            })
        }
    }

    operator fun invoke(
        error: (Failure) -> Unit = {},
        success: (Type) -> Unit = {}
    ) = this.invoke(error, null, Dispatchers.Main, success)

    private fun invoke(
        params: Params? = null,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val job = GlobalScope.async {
            run(params)
        }
        GlobalScope.launch(dispatcher) {
            onResult.invoke(job.await())
        }
    }

    fun runBlocking(params: Params? = null): Either<Failure, Type> {
        return kotlinx.coroutines.runBlocking {
            val job = async {
                run(params)
            }
            job.await()
        }
    }
}