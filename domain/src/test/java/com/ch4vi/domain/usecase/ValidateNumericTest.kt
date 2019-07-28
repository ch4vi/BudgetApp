package com.ch4vi.domain.usecase

import com.ch4vi.domain.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class ValidateNumericTest {

    private val valid = "12345"
    private val invalid = listOf("a", "11w2", "11,2", "11.2", "")

    private lateinit var case: ValidateNumeric

    @Before
    fun setUp() {
        case = ValidateNumeric()
    }

    @Test
    fun `should validate valid number`() {

        val either = case.runBlocking(ValidateNumeric.Params(valid))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBe true
    }

    @Test
    fun `should validate invalid number`() {

        case.runBlocking(ValidateNumeric.Params(invalid[0])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateNumeric.Params(invalid[1])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateNumeric.Params(invalid[2])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateNumeric.Params(invalid[3])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateNumeric.Params(invalid[4])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }
    }

    @Test
    fun `should ValidateNumeric return failure`() {

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.ParamsFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.ParamsFailure()
    }
}
