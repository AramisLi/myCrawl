package com.lizhidan.myandroidcrawler.crawler.httpparse

import com.lizhidan.myandroidcrawler.extensions.logE
import org.jsoup.nodes.Document

/**
 * IpzParser
 * Created by lizhidan on 2018/3/7.
 */
class IpzParser:HtmlParser<IpzParser> {
    override fun parse(document: Document, requestQueue: MutableCollection<String>,isRoot:Boolean): IpzParser? {
        logE(document.baseUri())
        if (isRoot){

            document.getElementsByTag("a").filter{
                val re="^.*list&.*".toRegex()
                re.matches(it.attr("href"))
            }.forEach {
                        logE(""+it.attr("href"))
                        requestQueue.add(document.baseUri()+it.attr("href"))
                    }
            return null
        }else{

        }
        return null
    }
}
