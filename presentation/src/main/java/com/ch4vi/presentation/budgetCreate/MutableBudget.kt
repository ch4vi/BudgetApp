package com.ch4vi.presentation.budgetCreate

import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location

data class MutableBudget(
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var category: Category? = null,
    var subcategory: Category? = null,
    var location: Location? = null,
    var description: String? = null
)