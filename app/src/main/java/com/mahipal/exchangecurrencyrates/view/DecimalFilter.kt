package com.mahipal.exchangecurrencyrates.view

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

class DecimalFilter(
    private val beforeDecimal: Int,
    private val afterDecimal: Int
) : InputFilter {

    private val mPattern = Pattern.compile("[0-9]{0," + (beforeDecimal-1) + "}+((\\.[0-9]{0," + (afterDecimal-1) + "})?)||(\\.)?")

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }
}