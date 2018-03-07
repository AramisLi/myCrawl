package com.lizhidan.myandroidcrawler.crawler.httpparse

import org.jsoup.nodes.Document

/**
 * HtmlParser
 * Created by lizhidan on 2018/2/27.
 */
interface HtmlParser<out T> {
    fun parse(document: Document,requestQueue:MutableCollection<String>,isRoot:Boolean=false):T?
}