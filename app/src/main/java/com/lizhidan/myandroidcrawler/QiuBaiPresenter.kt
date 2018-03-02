package com.lizhidan.myandroidcrawler

import android.util.Log
import com.lizhidan.myandroidcrawler.base.BasePresenter
import com.lizhidan.myandroidcrawler.base.BaseView
import com.lizhidan.myandroidcrawler.bean.QiuBaiBean
import com.lizhidan.myandroidcrawler.crawler.HtmlParser
import com.lizhidan.myandroidcrawler.crawler.HttpCrawler
import com.lizhidan.myandroidcrawler.crawler.QiubaiHtmlParser
import com.lizhidan.myandroidcrawler.extensions.logE
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**QiuBaiPresenter
 * Created by lizhidan on 2018/2/27.
 */
class QiuBaiPresenter(view: MainView) : BasePresenter<MainView>(view) {
    private val defaultUrl = "https://www.qiushibaike.com/8hr/page/"
    private var page = 1
    fun startCraw(url: String = defaultUrl) {

        HttpCrawler().startCrawl(mView,url + page,QiubaiHtmlParser(),{
            logE("回调2 in")
            mView?.onGetDataSuccess(it)
        },{errorCode, errorMsg ->  mView?.onGetDataFail(errorCode,errorMsg)})
    }

    fun startCrawTest(url: String){
        HttpCrawler().startCrawl(mView,url,object :HtmlParser<String>{
            override fun parse(document: Document): List<String> {
                logE(document.toString())
                return mutableListOf()
            }

        },{
            logE("回调2 in")
        },{errorCode, errorMsg ->  mView?.onGetDataFail(errorCode,errorMsg)})
    }
}

interface MainView : BaseView {
    fun onGetDataSuccess(list: List<QiuBaiBean>)
    fun onGetDataFail(errorCode: Int, errorMsg: String)
}
