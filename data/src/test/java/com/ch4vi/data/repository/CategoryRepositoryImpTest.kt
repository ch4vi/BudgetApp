package com.ch4vi.data.repository

import com.ch4vi.data.createCategory
import com.ch4vi.data.createSubcategory
import com.ch4vi.data.datasource.CategoryApiDataSource
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
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

class CategoryRepositoryImpTest {

    private val api: CategoryApiDataSource = mock()
    private lateinit var repository: CategoryRepositoryImp

    @Before
    fun setUp() {
        repository = CategoryRepositoryImp(api)
    }

    @Test
    fun `should get category list from network`() {
        When calling api.getCategoryList() `it returns`
                Either.Success(listOf(createCategory()))

        val either = repository.getCategories()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createCategory()
    }

    @Test
    fun `should getCategories return failure`() {
        When calling api.getCategoryList() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = repository.getCategories()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }

    @Test
    fun `should get subcategory list from network`() {
        When calling api.getSubcategoryList(any()) `it returns`
                Either.Success(listOf(createSubcategory(parentId = "qux")))

        val either = repository.getSubcategories("qux")

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createSubcategory(parentId = "qux")
    }

    @Test
    fun `should getSubcategories return failure`() {
        When calling api.getSubcategoryList(any()) `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = repository.getSubcategories("qux")

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }
}
