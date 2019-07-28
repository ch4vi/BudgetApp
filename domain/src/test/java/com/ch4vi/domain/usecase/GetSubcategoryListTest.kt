package com.ch4vi.domain.usecase

import com.ch4vi.domain.createSubcategory
import com.ch4vi.domain.repository.CategoryRepository
import com.ch4vi.domain.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class GetSubcategoryListTest {

    private val repository: CategoryRepository = mock()
    private lateinit var case: GetSubcategoryList

    @Before
    fun setUp() {
        case = GetSubcategoryList(repository)
    }

    @Test
    fun `should get subcategory list`() {
        When calling repository.getSubcategories(any()) `it returns`
                Either.Success(listOf(createSubcategory(parentId = "qux")))

        val either = case.runBlocking(GetSubcategoryList.Params("qux"))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createSubcategory(parentId = "qux"))
    }

    @Test
    fun `should GetSubcategoryList return failure`() {
        When calling repository.getSubcategories(any()) `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = case.runBlocking(GetSubcategoryList.Params("qux"))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }

    @Test
    fun `should GetSubcategoryList return params failure`() {

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.ParamsFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.ParamsFailure()
    }
}
