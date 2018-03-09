package com.lizhidan.myandroidcrawler.crawler.httpparse

import com.lizhidan.myandroidcrawler.bean.YellowBean
import org.jsoup.nodes.Document

/**
 * YellowHttpParser
 * Created by lizhidan on 2018/3/2.
 */
class YellowHttpParser(override var baseUrl: String) : HtmlParser<YellowBean> {
    override fun parse(url: String, document: Document, requestQueue: MutableCollection<String>, isRoot: Boolean): YellowBean? {
        return null
    }

//    override fun parse(document: Document,requestQueue:MutableCollection<String>,isRoot:Boolean): YellowBean? {
//        val list= mutableListOf<YellowBean>()
//        val elementsByTag = document.getElementsByTag("textarea")
//        for (element in elementsByTag) {
//            val bean=YellowBean()
//            val elementStr = element.toString()
//            val start=elementStr.indexOf("f:\"")
//            val end=elementStr.indexOf(".mp4")
//            if (start in 1..(end - 1)){
//                bean.imgAddr=elementStr.substring(IntRange(start+3,end+4))
//            }
//            list.add(bean)
//        }

//        return null
//    }
}