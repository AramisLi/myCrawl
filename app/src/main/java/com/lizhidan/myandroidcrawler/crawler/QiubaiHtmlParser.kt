package com.lizhidan.myandroidcrawler.crawler

import com.lizhidan.myandroidcrawler.bean.QiuBaiBean
import com.lizhidan.myandroidcrawler.extensions.logE
//import com.lizhidan.myandroidcrawler.extensions.logE
import org.jsoup.nodes.Document

/**
 * QiubaiHtmlParser
 * Created by lizhidan on 2018/2/27.
 */
class QiubaiHtmlParser:HtmlParser<QiuBaiBean> {
    override fun parse(document: Document): List<QiuBaiBean> {
        logE("解析in")
        val list = mutableListOf<QiuBaiBean>()
        val contentDoc = document.getElementById("content-left")
        val children = contentDoc.children()
//        logE(children.size.toString())
        children.filter { it.nodeName() == "div" }
                .forEach {
                    val bean = QiuBaiBean()
                    val authorDiv=it.getElementsByClass("author clearfix").first()
//                    if (authorDiv!=null)
//                        logE(authorDiv.toString())
                    //作者
//                            val author=it.getElementsByTag("h2")
                    val author =authorDiv.getElementsByTag("h2")
                    bean.author=author?.text()?:""
                    //作者头像
                    val avatar=it.select("img")?.attr("src")
                    if (avatar?.isNotBlank()==true){
                        bean.avatar="https:"+avatar
                    }
                    //内容文字
                    val content = it.select(".content")?.text() ?: ""
                    bean.content = content
                    //图片
                    val img=it.select(".thumb img[src$=jpg]")?.attr("src")
//                            logE(img)
                    if (img?.isNotBlank() == true){
                        bean.img="https:"+img
//                                logE("in "+bean.img)
                    }

                    list.add(bean)
                }
        logE("解析完成in" + list.size)
        return list
    }
}