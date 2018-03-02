package com.lizhidan.myandroidcrawler.crawler

import android.content.Context
import android.os.Handler
import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.extensions.logE
import com.lizhidan.myandroidcrawler.extensions.shift
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.regex.Pattern

/**
 * YellowCrawlExecuter 循环爬取
 * Created by lizhidan on 2018/3/2.
 */
class YellowCrawlExecuter {
    companion object {
        val HANDLE_RUNNING = 0
        val HANDLE_SEND = 1
        val HANDLE_COMPLETE = 2
        val HANDLE_ERROR = 3
    }

    //    private val crawler: HttpCrawler = HttpCrawler()
    private val baseUrl = "http://www.46ek.com"
    private val urls = mutableSetOf<String>()
    private val urlsOld = mutableSetOf<String>()
    private val yellowHttpParser = YellowHttpParser()
    private val results = mutableListOf<YellowBean>()

    fun executeCrawl(url: String? = null, handler: Handler? = null) {
        doAsync {
            crawl(url ?: baseUrl, handler)
        }
    }

    private fun crawl(url: String, handler: Handler?) {
        urlsOld.add(url)
        logE("开始爬取：$url")
//        Jsoup.connect(url)
        val document: Document = getDocument(url)
        logE(document.toString())

        if (url == baseUrl) {
            val urlTags = document.getElementsByTag("a")
            val list = urlTags.filter {
                //                logE("链接："+it.attr("href"))
                val regex = "^/list/8.*$".toRegex()
                regex.matches(it.attr("href"))
            }.map { baseUrl + it.attr("href") }
            urls.addAll(list)
            logE(urls.toString())
            val first = urls.shift()
            crawl(first, handler)
//            logE("${urls.size}"+","+first.toString())
        } else {
            val urlTags = document.getElementsByTag("a")
            for (link in urlTags) {
                val href = link.attr("href")
                logE(href)
            }

            val mp4s = yellowHttpParser.parse(document)
            if (mp4s.isNotEmpty()) {
                this.results.addAll(mp4s)
                val obtainMessage = handler?.obtainMessage()
                obtainMessage?.obj = mp4s
                obtainMessage?.what = HANDLE_SEND
                handler?.sendMessage(obtainMessage)
            }
        }


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
        con.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6")
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