package com.lizhidan.myandroidcrawler.crawler

import org.jsoup.nodes.Document

/**
 * HtmlParser
 * Created by lizhidan on 2018/2/27.
 */
interface HtmlParser<out T> {
    fun parse(document: Document):List<T>
}