package com.lizhidan.myandroidcrawler.extensions

import android.util.Log

/**
 * AramisExtension
 * Created by lizhidan on 2018/2/27.
 */


fun Any.logE(str: String?="null") {
    Log.e("===${javaClass.simpleName}===", str)
}

fun <T>MutableCollection<T>.shift(): T {
    val first = this.first()
    this.remove(first)
    return first
}

fun <T>MutableCollection<T>.pop():T{
    val last = this.last()
    this.remove(last)
    return last
}
