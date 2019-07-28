package com.ch4vi.data.datasource

import com.ch4vi.data.api.ApiService
import com.ch4vi.data.api.entity.ApiLocation
import com.ch4vi.data.createApiLocation
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
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


class LocationApiDataSourceTest {
    private val api: ApiService = mock()
    private lateinit var datasource: LocationApiDataSourceImp

    @Before
    fun setUp() {
        datasource = LocationApiDataSourceImp(api)
    }


    @Test
    fun `should get location list from network`() {
        val call: Call<List<ApiLocation>> = mock()
        When calling api.getLocationList() `it returns` call

        When calling api.getLocationList().execute() `it returns`
                Response.success(listOf(createApiLocation(id = "qux")))

        val either = datasource.getLocationList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createApiLocation(id = "qux")
    }

    @Test
    fun `should getCategoryList return failure`() {
        val call: Call<List<ApiLocation>> = mock()
        When calling api.getLocationList() `it returns` call

        When calling api.getLocationList().execute() `it returns`
                Response.error(400, "test".toResponseBody())

        val either = datasource.getLocationList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.RequestFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.RequestFailure("400 test")
    }
}