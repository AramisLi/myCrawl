package com.lizhidan.myandroidcrawler.bean

import java.io.Serializable

/**
 * bean
 * Created by lizhidan on 2018/2/27.
 */

data class QiuBaiBean(var title: String = "", var author: String = "匿名用户",
                      var content: String = "",
                      var img: String = "",var avatar:String="") : Serializable