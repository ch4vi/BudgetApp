package com.ch4vi.data.repository

import com.ch4vi.data.createBudget
import com.ch4vi.data.datasource.BudgetDatabaseDataSource
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class BudgetRepositoryImpTest {

    private val database: BudgetDatabaseDataSource = mock()
    private lateinit var repository: BudgetRepositoryImp

    @Before
    fun setUp() {
        repository = BudgetRepositoryImp(database)
    }

    @Test
    fun `should get budget list from database`() {
        When calling database.getBudgetList() `it returns`
                Either.Success(listOf(createBudget()))

        val either = repository.getBudgetList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createBudget()
    }

    @Test
    fun `should getBudgetList return failure`() {
        When calling database.getBudgetList() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = repository.getBudgetList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }

    @Test
    fun `should create budget in database`() {
        repository.createBudget(createBudget(name = "qux"))

        verify(database).createBudget(createBudget(name = "qux"))
    }
}
