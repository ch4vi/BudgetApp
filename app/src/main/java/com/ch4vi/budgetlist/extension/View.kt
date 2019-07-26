package com.ch4vi.budgetlist.extension

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: CharSequence) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .apply {
            val textView =
                this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(Color.WHITE)
        }.show()
}