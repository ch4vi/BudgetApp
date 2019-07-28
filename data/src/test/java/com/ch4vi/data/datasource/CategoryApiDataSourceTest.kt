package com.ch4vi.data.datasource

import com.ch4vi.data.api.ApiService
import com.ch4vi.data.api.entity.ApiCategory
import com.ch4vi.data.createApiCategory
import com.ch4vi.data.createApiSubcategory
import com.ch4vi.data.createCategory
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response


class CategoryApiDataSourceTest {
    private val api: ApiService = mock()
    private lateinit var datasource: CategoryApiDataSourceImp

    @Before
    fun setUp() {
        datasource = CategoryApiDataSourceImp(api)
    }


    @Test
    fun `should get category list from network`() {
        val call: Call<List<ApiCategory>> = mock()
        When calling api.getCategoryList() `it returns` call

        When calling api.getCategoryList().execute() `it returns`
                Response.success(listOf(createApiCategory(id = "qux")))

        val either = datasource.getCategoryList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createCategory(id = "qux")
    }

    @Test
    fun `should getCategoryList return failure`() {
        val call: Call<List<ApiCategory>> = mock()
        When calling api.getCategoryList() `it returns` call

        When calling api.getCategoryList().execute() `it returns`
                Response.error(400, "test".toResponseBody())

        val either = datasource.getCategoryList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.RequestFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.RequestFailure("400 test")
    }

    @Test
    fun `should get subcategory list from network`() {
        val call: Call<List<ApiCategory>> = mock()
        When calling api.getSubcategoryList(any()) `it returns` call

        When calling api.getSubcategoryList("").execute() `it returns`
                Response.success(listOf(createApiSubcategory(parentId = "qux")))

        val either = datasource.getSubcategoryList("qux")

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createApiSubcategory(parentId = "qux")
    }

    @Test
    fun `should getSubcategoryList return failure`() {
        val call: Call<List<ApiCategory>> = mock()
        When calling api.getSubcategoryList(any()) `it returns` call

        When calling api.getSubcategoryList("").execute() `it returns`
                Response.error(400, "test".toResponseBody())

        val either = datasource.getSubcategoryList("qux")

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.RequestFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.RequestFailure("400 test")
    }
}