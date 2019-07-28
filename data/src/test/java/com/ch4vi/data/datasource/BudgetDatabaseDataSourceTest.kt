package com.ch4vi.data.datasource

import com.ch4vi.data.createBudget
import com.ch4vi.data.createDbBudget
import com.ch4vi.data.database.AppDatabase
import com.ch4vi.data.database.dao.BudgetDao
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class BudgetDatabaseDataSourceTest {

    private val budgetDao: BudgetDao = mock()
    private val database: AppDatabase = mock()
    private lateinit var datasource: BudgetDatabaseDataSourceImp

    @Before
    fun setUp() {
        datasource = BudgetDatabaseDataSourceImp(database)
    }

    @Test
    fun `should get budget from database`() {
        When calling database.budgetDao() `it returns` budgetDao

        When calling database.budgetDao().fetchBudget(any()) `it returns`
                createDbBudget(id = 1, name = "qux")

        val either = datasource.getBudget(1)

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField createBudget("qux")
    }

    @Test
    fun `should getBudget return failure`() {
        When calling database.budgetDao() `it returns` budgetDao

        When calling database.budgetDao().fetchBudget(any()) `it returns`
                createDbBudget(name = null)

        val either = datasource.getBudget(1)

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.MapperFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.MapperFailure("name")
    }


    @Test
    fun `should get budget list from database`() {
        When calling database.budgetDao() `it returns` budgetDao

        When calling database.budgetDao().fetchAll() `it returns`
                listOf(createDbBudget(name = "qux"))

        val either = datasource.getBudgetList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createBudget("qux")
    }

    @Test
    fun `should getBudgetList return failure`() {
        When calling database.budgetDao() `it returns` budgetDao

        When calling database.budgetDao().fetchAll() `it returns`
                listOf(createDbBudget(name = null))

        val either = datasource.getBudgetList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.MapperFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.MapperFailure("name")
    }

    @Test
    fun `should create budget in database`() {
        When calling database.budgetDao() `it returns` budgetDao

        datasource.createBudget(createBudget(name = "qux"))

        verify(budgetDao).insertBudget(createDbBudget(name = "qux"))
    }
}