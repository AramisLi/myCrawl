package com.lizhidan.myandroidcrawler.crawler

import com.lizhidan.myandroidcrawler.base.BaseView

/**
 * BaseHttpCrawler
 * Created by lizhidan on 2018/3/2.
 */
interface BaseHttpCrawler {
    fun <T>startCrawl(context: BaseView?, url:String, parser: HtmlParser<T>, success:((result:List<T>)->Unit)?,
                      fail:((errorCode:Int,errorMsg:String)->Unit)?)
}