package com.aramis.library.ui

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan

/**
 * AramisUtils
 * Created by lizhidan on 2018/3/9.
 */
object AramisUtils {
    fun getColoredString(str: String, start: Int, end: Int, color: Int): SpannableStringBuilder {
        val builder = SpannableStringBuilder(str)
        builder.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder
    }

    fun getColoredString(spanBuilder: SpannableStringBuilder, start: Int, end: Int, color: Int): SpannableStringBuilder {
        spanBuilder.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanBuilder
    }


    fun getColoredString(str: String, start: IntArray, end: IntArray, color: IntArray): SpannableStringBuilder {
        val builder = SpannableStringBuilder(str)
        for (i in start.indices) {
            builder.setSpan(ForegroundColorSpan(color[i]), start[i], end[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return builder
    }
}