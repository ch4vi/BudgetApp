package com.ch4vi.data.datasource

import com.ch4vi.data.createDbLocation
import com.ch4vi.data.createLocation
import com.ch4vi.data.database.AppDatabase
import com.ch4vi.data.database.dao.LocationDao
import com.ch4vi.data.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.nhaarman.mockitokotlin2.any
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

class LocationDatabaseDataSourceTest {

    private val locationDao: LocationDao = mock()
    private val database: AppDatabase = mock()
    private lateinit var datasource: LocationDatabaseDataSourceImp

    @Before
    fun setUp() {
        datasource = LocationDatabaseDataSourceImp(database)
    }

    @Test
    fun `should get location list from database`() {
        When calling database.locationDao() `it returns` locationDao

        When calling database.locationDao().fetchAll() `it returns`
                listOf(createDbLocation(id = "qux"))

        val either = datasource.getLocationList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createLocation("qux")
    }

    @Test
    fun `should getBudgetList return failure`() {
        When calling database.locationDao() `it returns` locationDao

        When calling database.locationDao().fetchAll() `it returns`
                listOf(createDbLocation(name = null))

        val either = datasource.getLocationList()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.MapperFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.MapperFailure("name")
    }

    @Test
    fun `should find location list from database`() {
        When calling database.locationDao() `it returns` locationDao

        When calling database.locationDao().findLocations(any()) `it returns`
                listOf(createDbLocation(id = "qux"))

        val either = datasource.findLocations("qux")

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result!!.first() shouldBeEqualToComparingFieldByField createLocation("qux")
    }

    @Test
    fun `should findLocations return failure`() {
        When calling database.locationDao() `it returns` locationDao

        When calling database.locationDao().findLocations(any()) `it returns`
                listOf(createDbLocation(name = null))

        val either = datasource.findLocations("qux")

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.MapperFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.MapperFailure("name")
    }

    @Test
    fun `should update locations in database`() {
        When calling database.locationDao() `it returns` locationDao

        datasource.updateLocationList(listOf(createLocation(id = "qux")))

        verify(locationDao).insertLocation(listOf(createDbLocation(id = "qux")))
    }

    @Test
    fun `should count locations in database`() {
        When calling database.locationDao() `it returns` locationDao

        When calling database.locationDao().countLocations() `it returns` 42

        val result = datasource.countLocations()

        verify(locationDao).countLocations()
        result shouldBe 42
    }

    @Test
    fun `should clear locations in database`() {
        When calling database.locationDao() `it returns` locationDao

        datasource.clearLocations()

        verify(locationDao).deleteAll()
    }
}