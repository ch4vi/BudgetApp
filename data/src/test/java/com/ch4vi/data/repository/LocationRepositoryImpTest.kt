package com.ch4vi.data.repository

import com.ch4vi.data.createLocation
import com.ch4vi.data.datasource.LocationApiDataSource
import com.ch4vi.data.datasource.LocationDatabaseDataSource
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class LocationRepositoryImpTest {

    private val database: LocationDatabaseDataSource = mock()
    private val api: LocationApiDataSource = mock()
    private lateinit var repository: LocationRepositoryImp

    @Before
    fun setUp() {
        repository = LocationRepositoryImp(api, database)
    }

    @Test
    fun `should get location list from database`() {
        When calling database.getLocationList() `it returns`
                Either.Success(listOf(createLocation()))

        val either = repository.getLocationsFromDatabase()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createLocation()
    }

    @Test
    fun `should getLocationsFromDatabase return failure`() {
        When calling database.getLocationList() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = repository.getLocationsFromDatabase()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }

    @Test
    fun `should get location list from network`() {
        When calling api.getLocationList() `it returns`
                Either.Success(listOf(createLocation()))

        val either = repository.getLocationsFromApi()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createLocation()
    }

    @Test
    fun `should getLocationsFromApi return failure`() {
        When calling api.getLocationList() `it returns`
                Either.Error(Failure.GenericFailure("foo"))

        val either = repository.getLocationsFromApi()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.GenericFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.GenericFailure("foo")
    }

    @Test
    fun `should update locations in database`() {
        repository.updateLocations(listOf(createLocation(id = "qux")))

        verify(database).clearLocations()
        verify(database).updateLocationList(listOf(createLocation(id = "qux")))
    }

    @Test
    fun `should check if locations are empty in database`() {
        When calling database.countLocations() `it returns` 0

        val result = repository.isDatabaseEmpty()

        result shouldNotBe null
        result shouldBe true
    }

    @Test
    fun `should find locations in database`() {
        repository.findLocations("qux")

        verify(database).findLocations("qux")
    }
}
