package com.ch4vi.domain.usecase

import com.ch4vi.domain.shouldBeEqualToComparingFieldByField
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class ValidateEmailTest {

    private val validEmail = "foo.bar@foobar.qux"
    private val invalidEmail = listOf("foo", "foo.bar", "foo.bar@", "foo@bar", "foo@bar.")

    private lateinit var case: ValidateEmail

    @Before
    fun setUp() {
        case = ValidateEmail()
    }

    @Test
    fun `should validate valid email`() {

        val either = case.runBlocking(ValidateEmail.Params(validEmail))

        either shouldNotBe null

        either shouldBeInstanceOf Either.Success::class.java
        val result = either.getSuccess()
        result shouldBe true
    }

    @Test
    fun `should validate invalid email`() {

        case.runBlocking(ValidateEmail.Params(invalidEmail[0])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateEmail.Params(invalidEmail[1])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateEmail.Params(invalidEmail[2])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateEmail.Params(invalidEmail[3])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }

        case.runBlocking(ValidateEmail.Params(invalidEmail[4])).apply {
            this shouldNotBe null

            this shouldBeInstanceOf Either.Success::class.java
            val result = this.getSuccess()
            result shouldBe false
        }
    }

    @Test
    fun `should ValidateEmail return failure`() {

        val either = case.runBlocking()

        either shouldNotBe null

        either shouldBeInstanceOf Either.Error::class.java
        val result = either.getError()
        result shouldBeInstanceOf Failure.ParamsFailure::class.java
        result shouldBeEqualToComparingFieldByField Failure.ParamsFailure()
    }
}
