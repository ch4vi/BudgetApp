package com.ch4vi.domain.usecase

import com.ch4vi.domain.createLocation
import com.ch4vi.domain.repository.LocationRepository
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

class FindLocationsTest {

    private val repository: LocationRepository = mock()
    private lateinit var case: FindLocations

    @Before
    fun setUp() {
        case = FindLocations(repository)
    }

    @Test
    fun `should get location list`() {
        When calling repository.findLocations(any()) `it returns`
                Either.Success(listOf(createLocation(id = "qux")))

        val either = case.runBlocking(FindLocations.Params("qux"))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createLocation(id = "qux"))
    }

    @Test
    fun `should FindLocations return failure`() {
        When calling repository.findLocations(any()) `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = case.runBlocking(FindLocations.Params("qux"))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }

    @Test
    fun `should FindLocations return params failure`() {

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.ParamsFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.ParamsFailure()
    }
}
