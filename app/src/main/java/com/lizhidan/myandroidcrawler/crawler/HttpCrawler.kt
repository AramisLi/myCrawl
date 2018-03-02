package com.lizhidan.myandroidcrawler.crawler

import com.lizhidan.myandroidcrawler.base.BaseView
import com.lizhidan.myandroidcrawler.extensions.logE
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

/**
 * HttpCrawler
 * Created by lizhidan on 2018/2/27.
 */
class HttpCrawler : BaseHttpCrawler {
    override fun <T> startCrawl(context: BaseView?, url: String, parser: HtmlParser<T>, success: ((result: List<T>) -> Unit)?,
                                fail: ((errorCode: Int, errorMsg: String) -> Unit)?) {
        context?.doAsync {
            try {

                val document: Document = Jsoup.connect(url).get()
                logE("获取到数据in")
                val result = parser.parse(document)
                uiThread {
                    logE("回调in")
                    success?.invoke(result)
                }
            } catch (e: Exception) {
                uiThread {
                    fail?.invoke(109, e.toString())
                }
            }

        }
    }


}