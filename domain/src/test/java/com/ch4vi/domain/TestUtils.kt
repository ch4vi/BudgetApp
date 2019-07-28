package com.ch4vi.domain

import org.assertj.core.api.Assertions

internal infix fun <T> T.shouldBeEqualToComparingFieldByField(expected: T?) = this.apply {
    Assertions.assertThat(this).isEqualToComparingFieldByFieldRecursively(expected)
}