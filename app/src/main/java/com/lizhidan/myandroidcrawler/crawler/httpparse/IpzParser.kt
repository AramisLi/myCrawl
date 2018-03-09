package com.lizhidan.myandroidcrawler.crawler.httpparse

import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.extensions.logE
import org.jsoup.nodes.Document

/**
 * IpzParser
 * Created by lizhidan on 2018/3/7.
 */
class IpzParser(override var baseUrl: String) : HtmlParser<YellowBean> {
    private val listRegex = "^.*list.*$".toRegex()
    private val detailRegex = "^.*view/index.*$".toRegex()
    private val playRegex = "^.*/player.*".toRegex()
    private val dataRegex = "^.*/playdata.*$".toRegex()

    private var yellowBean: YellowBean? = null
    var isTest = false
    private val testUrlSet = mutableSetOf<String>()

    override fun parse(url: String, document: Document, requestQueue: MutableCollection<String>, isRoot: Boolean): YellowBean? {
        logE("开始解析:" + isRoot)
        logE("baseUri:" + document.baseUri())
        testUrlSet.clear()
        if (url == baseUrl) {
            //根目录，添加列表url
//            val list = document.getElementsByTag("a")
//            logE(list.size.toString())
//            list.filter {
//                listRegex.matches(it.attr("href"))
//            }.forEach {
//                        requestQueue.add(baseUrl + it.attr("href"))
//                    }

            filterLinks(document, "a", listRegex, "href", requestQueue)
        } else {
            when {
            //1.列表结果，添加详情url（只爬取第一页） /view/index
                listRegex.matches(url) -> {
//                    if ("^/list/index1/.*$".toRegex().matches(url))
//                        logE(document.toString())
                    document.getElementsByTag("a").filter {
                        detailRegex.matches(it.attr("href"))
                    }.forEach {
                                requestQueue.add(baseUrl + it.attr("href"))
                            }
                }
            //2.详情结果，添加播放页url
                detailRegex.matches(url) -> {
                    document.getElementsByTag("a").filter {
                        playRegex.matches(it.attr("href"))
                    }.forEach {
                                requestQueue.add(baseUrl + it.attr("href"))
                            }
                }

            //3.播放页结果，拿到js代码，截取播放地址，return bean
                playRegex.matches(url) -> {
//                    /playdata
                    val dataUrl=document.getElementsByTag("script").filter {
                        dataRegex.matches(it.attr("src"))
                    }.map {
                                baseUrl + it.attr("src")
                            }[0]

//                            .forEach {
////                                requestQueue.add(baseUrl + it.attr("src"))
//
//
//                            }
                    return YellowBean("数据",dataUrl)
                }
            //4.截取数据，返回
                dataRegex.matches(url) -> {
                    val bodyStr = document.body().toString()
                    logE("视频数据")
                    logE(bodyStr)
                }
            }

        }

//        if (isTest) {
//            return
//        }
        return null
    }

    private fun filterLinks(document: Document, tag: String, regex: Regex, attrStr: String,
                            requestQueue: MutableCollection<String>) {
        var index = 0
        document.getElementsByTag(tag).filter {
            regex.matches(it.attr(attrStr))
        }.forEach {
                    val url = baseUrl + it.attr(attrStr)
//                    if (isTest){
//                        testUrlSet.add(url)
//                        if (index==0){
//                            index++
//                            requestQueue.add(url)
//                        }
//                    }else{
                    requestQueue.add(url)
//                    }
                }
    }
}
