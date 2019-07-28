package com.ch4vi.domain.usecase

import com.ch4vi.domain.createLocation
import com.ch4vi.domain.repository.LocationRepository
import com.ch4vi.domain.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class GetLocationListTest {

    private val repository: LocationRepository = mock()
    private lateinit var case: GetLocationList

    @Before
    fun setUp() {
        case = GetLocationList(repository)
    }

    @Test
    fun `should get locations list first time`() {
        When calling repository.isDatabaseEmpty() `it returns` true

        When calling repository.getLocationsFromApi() `it returns`
                Either.Success(listOf(createLocation(id = "qux")))

        val either = case.runBlocking(GetLocationList.Params(false))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createLocation(id = "qux"))

        verify(repository).updateLocations(listOf(createLocation(id = "qux")))
        verify(repository, never()).getLocationsFromDatabase()
    }

    @Test
    fun `should get locations list force update`() {
        When calling repository.isDatabaseEmpty() `it returns` false

        When calling repository.getLocationsFromApi() `it returns`
                Either.Success(listOf(createLocation(id = "qux")))

        val either = case.runBlocking(GetLocationList.Params(true))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createLocation(id = "qux"))

        verify(repository).updateLocations(listOf(createLocation(id = "qux")))
        verify(repository, never()).getLocationsFromDatabase()
    }

    @Test
    fun `should get locations list without update`() {
        When calling repository.isDatabaseEmpty() `it returns` false

        When calling repository.getLocationsFromDatabase() `it returns`
                Either.Success(listOf(createLocation(id = "qux")))

        val either = case.runBlocking(GetLocationList.Params(false))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBeEqualToComparingFieldByField listOf(createLocation(id = "qux"))

        verify(repository, never()).updateLocations(any())
        verify(repository, never()).getLocationsFromApi()
    }

    @Test
    fun `should GetSubcategoryList return failure`() {
        When calling repository.getLocationsFromDatabase() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = case.runBlocking(GetLocationList.Params(false))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }
}
