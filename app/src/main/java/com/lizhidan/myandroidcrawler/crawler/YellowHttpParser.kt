package com.lizhidan.myandroidcrawler.crawler

import com.lizhidan.myandroidcrawler.bean.YellowBean
import org.jsoup.nodes.Document

/**
 * YellowHttpParser
 * Created by lizhidan on 2018/3/2.
 */
class YellowHttpParser:HtmlParser<YellowBean> {
    override fun parse(document: Document): List<YellowBean> {
        val list= mutableListOf<YellowBean>()
        val elementsByTag = document.getElementsByTag("textarea")
        for (element in elementsByTag) {
            val bean=YellowBean()
            val elementStr = element.toString()
            val start=elementStr.indexOf("f:\"")
            val end=elementStr.indexOf(".mp4")
            if (start in 1..(end - 1)){
                bean.imgAddr=elementStr.substring(IntRange(start+3,end+4))
            }
            list.add(bean)
        }

        return list
    }
}