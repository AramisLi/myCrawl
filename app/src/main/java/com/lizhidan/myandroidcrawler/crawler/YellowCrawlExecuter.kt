package com.lizhidan.myandroidcrawler.crawler

import android.os.Handler
import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.bean.YellowResIds
import com.lizhidan.myandroidcrawler.bean.YellowResListBean
import com.lizhidan.myandroidcrawler.crawler.httpparse.HtmlParser
import com.lizhidan.myandroidcrawler.crawler.httpparse.IpzParser
import com.lizhidan.myandroidcrawler.extensions.logE
import com.lizhidan.myandroidcrawler.extensions.pop
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

/**
 * YellowCrawlExecuter 循环爬取
 * Created by lizhidan on 2018/3/2.
 */
class YellowCrawlExecuter {
    companion object {
        const val HANDLE_RUNNING = 0
        const val HANDLE_SEND = 1 //发送单个数据
        const val HANDLE_COMPLETE = 2 //爬取完成
        const val HANDLE_ERROR_LINK = 3 //一个链接爬取失败
        const val HANDLE_FAIL = 4 //访问失败

        const val HANDLE_TEST = 5// 测试模式
    }

    var isTest = false
    private var testExecutTime = 0

    //    private val crawler: HttpCrawler = HttpCrawler()
    private var baseUrl = ""
    //先使用set，后期考虑使用栈
    private val urls = mutableSetOf<String>()
    private val urlsOld = mutableSetOf<String>()
    //    private val yellowHttpParser = YellowHttpParser()
    private val results = mutableListOf<YellowBean>()

    fun executeCrawl(url: String, htmlParser: HtmlParser<*>, handler: Handler) {
        urls.clear()
        logE("test执行 url:$url")
        this.baseUrl=htmlParser.baseUrl
        crawl(url, htmlParser, handler)
    }

    fun executeCrawl(bean: YellowResListBean, handler: Handler) {
        baseUrl =  bean.source
//        urls.add(baseUrl+"/list/index1.html")
        urls.add(baseUrl)
        val htmlParser = when (bean.id) {
            YellowResIds.RedFirst -> {
                IpzParser(baseUrl)
            }
            YellowResIds.RedSecond -> {
                IpzParser(baseUrl)
            }
            YellowResIds.RedThird -> {
                IpzParser(baseUrl)
            }
            else -> null
        }
        if (htmlParser is IpzParser) {
            htmlParser.isTest = this.isTest
        }
        doAsync {
            if (isTest) {
                testExecutTime++
                val url = urls.pop()
                logE("test url :$url urls.size:${urls.size}")
                if (testExecutTime > 1) {
                    urls.clear()
                }
                logE("test url :$url urls.size:${urls.size}")
                crawl(url, htmlParser, handler)
            } else {
                while (urls.isNotEmpty()) {
                    val url = urls.pop()
                    if (!urlsOld.contains(url)) {
                        crawl(url, htmlParser, handler)
                    }
                }
            }
            sendMessage(handler, null, HANDLE_COMPLETE)
        }
    }

    private fun <T> crawl(url: String, htmlParser: HtmlParser<T>?, handler: Handler?) {
        urlsOld.add(url)
        htmlParser?.logE("开始爬取：$url")
//        val document: Document = Jsoup.connect(url)
//                .header("User-Agent", "Mozilla/5.0").get()
        val isRoot = url == baseUrl
        try {
            val document: Document = Jsoup.connect(url).get()
            val bean = htmlParser?.parse(url, document, urls, isRoot)
            if (isTest) {
                sendMessage(handler, bean, HANDLE_TEST)
            } else {
                if (bean != null) {
                    sendMessage(handler, bean, HANDLE_SEND)
                }
            }
        } catch (e: Exception) {
            sendMessage(handler, null, if (isRoot) HANDLE_FAIL else HANDLE_ERROR_LINK)
        }

    }


    private fun sendMessage(handler: Handler?, obj: Any?, what: Int) {
        val obtainMessage = handler?.obtainMessage()
        if (isTest) {
            obtainMessage?.obj = this.urls
        } else {
            obtainMessage?.obj = obj
        }
        obtainMessage?.what = what
        handler?.sendMessage(obtainMessage)
    }

    private fun getDocument(url: String): Document {
        val con = Jsoup.connect(url)//获取请求连接
//      //浏览器可接受的MIME类型。
//        {'User-Agent':'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}
//        con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//        con.header("Accept-Encoding", "gzip, deflate")
//        con.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
//        con.header("Connection", "keep-alive")
//        con.header("Host", url)
//        con.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6")

        con.header("User-Agent", "Mozilla/5.0")
        return con.get()
    }

    fun addUrl() {

    }

    fun addMp4(document: Document) {
        val list = mutableListOf<YellowBean>()
        val elementsByTag = document.getElementsByTag("textarea")
        for (element in elementsByTag) {
            val bean = YellowBean()
            val elementStr = element.toString()
            val start = elementStr.indexOf("f:\"")
            val end = elementStr.indexOf(".mp4")
            if (start in 1..(end - 1)) {
                bean.imgAddr = elementStr.substring(IntRange(start + 3, end + 4))
            }
            list.add(bean)
        }
    }

}