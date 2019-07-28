package com.ch4vi.domain.usecase

import com.ch4vi.domain.createBudget
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class CreateBudgetTest {

    private val repository: BudgetRepository = mock()
    private lateinit var case: CreateBudget

    @Before
    fun setUp() {
        case = CreateBudget(repository)
    }

    @Test
    fun `should create budget`() {

        val either = case.runBlocking(CreateBudget.Params(createBudget(name = "qux")))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField Unit

        verify(repository).createBudget(createBudget(name = "qux"))
    }

    @Test
    fun `should CreateBudget return failure`() {

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.ParamsFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.ParamsFailure()
    }
}
