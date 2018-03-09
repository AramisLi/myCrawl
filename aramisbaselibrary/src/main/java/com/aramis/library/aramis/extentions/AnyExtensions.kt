package com.aramis.library.aramis.extentions

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import com.aramis.library.utils.LogUtils
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * 全局扩展
 * Created by Aramis on 2017/8/28.
 */
//fun Any.logE(msg: String) {
//    val TAG = "===" + javaClass.simpleName + "==="
//    LogUtils.e(TAG, msg)
//}

fun Any.logE(str: String? = "null") {
    logE("===${javaClass.simpleName}===", str)
}
fun Any.logE(tag: String = "",str: String? = "null") {
    LogUtils.e(tag, str)
}

fun keep(d: Double): String {
    val decimalFormat = DecimalFormat("#0.00")
    decimalFormat.roundingMode = RoundingMode.HALF_UP
    return decimalFormat.format(d)
}

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
