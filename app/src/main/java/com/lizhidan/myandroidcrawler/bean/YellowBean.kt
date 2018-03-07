package com.lizhidan.myandroidcrawler.bean

import java.io.Serializable

/**
 * YellowBean
 * Created by lizhidan on 2018/3/2.
 */
data class YellowBean(var title: String = "", var videoAddr: String = "", var imgAddr: String = "")

object YellowResIds{
    const val RedFirst=1
    const val RedSecond=2
    const val RedThird=3
}
data class YellowResListBean(val id: Int, val name: String, val source: String, val remark: String = ""
) : Serializable