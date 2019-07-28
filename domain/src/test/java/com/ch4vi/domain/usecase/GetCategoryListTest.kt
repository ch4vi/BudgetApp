package com.ch4vi.domain.usecase

import com.ch4vi.domain.createCategory
import com.ch4vi.domain.repository.CategoryRepository
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

class GetCategoryListTest {

    private val repository: CategoryRepository = mock()
    private lateinit var case: GetCategoryList

    @Before
    fun setUp() {
        case = GetCategoryList(repository)
    }

    @Test
    fun `should get category list`() {
        When calling repository.getCategories() `it returns`
                Either.Success(listOf(createCategory()))

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createCategory())
    }

    @Test
    fun `should GetCategoryList return failure`() {
        When calling repository.getCategories() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }
}
