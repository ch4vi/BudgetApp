package com.ch4vi.domain.usecase

import com.ch4vi.domain.createBudget
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class GetBudgetListTest {

    private val repository: BudgetRepository = mock()
    private lateinit var case: GetBudgetList

    @Before
    fun setUp() {
        case = GetBudgetList(repository)
    }

    @Test
    fun `should get budget list`() {
        When calling repository.getBudgetList() `it returns`
                Either.Success(listOf(createBudget()))

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createBudget())
    }

    @Test
    fun `should GetBudgetList return failure`() {
        When calling repository.getBudgetList() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }
}
