package com.lizhidan.myandroidcrawler.ui.pv

import android.os.Handler
import com.aramis.library.base.BasePresenter
import com.aramis.library.base.BaseView
import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.bean.YellowResListBean
import com.lizhidan.myandroidcrawler.crawler.YellowCrawlExecuter
import com.lizhidan.myandroidcrawler.crawler.httpparse.IpzParser
import com.lizhidan.myandroidcrawler.extensions.logE

/**
 * YellowPresenter
 * Created by lizhidan on 2018/3/2.
 */
class YellowPresenter(view: YellowView) : BasePresenter<YellowView>(view) {
    private val executer = YellowCrawlExecuter()
    private var errorLinkCount = 0
    private var successLinkCount = 0

    private val handler = Handler(Handler.Callback {
        when (it.what) {
            YellowCrawlExecuter.HANDLE_SEND -> {
                successLinkCount++
                logE("获取到一个数据：" + (it.obj as YellowBean).title)
                mView?.onCrawlMessage(it.obj as YellowBean, successLinkCount, errorLinkCount)
            }
            YellowCrawlExecuter.HANDLE_COMPLETE -> {
                mView?.onCrawlComplete(successLinkCount, errorLinkCount)
            }
            YellowCrawlExecuter.HANDLE_ERROR_LINK -> {
                errorLinkCount++
                mView?.onCrawlErrorLink(successLinkCount, errorLinkCount)
            }
            YellowCrawlExecuter.HANDLE_FAIL -> {
                mView?.onCrawlFail()
            }
            YellowCrawlExecuter.HANDLE_TEST->{
                if (it.obj is MutableSet<*>){
                    mView?.onCrawlTest(it.obj as MutableSet<String>)
                }else{
                    logE("测试获取："+it.obj.toString())
                }
            }
        }
        false
    })

    fun startCrawl(bean: YellowResListBean,isTest:Boolean=false) {
        executer.isTest = isTest
        executer.executeCrawl(bean, handler)
    }

    fun startCrawlTest(url:String,baseUrl:String){
        executer.executeCrawl(url,IpzParser(baseUrl), handler)
    }
}

interface YellowView:BaseView {
    fun onCrawlMessage(bean: YellowBean, successCount: Int, errorCount: Int)
    fun onCrawlComplete(successCount: Int, errorCount: Int)
    fun onCrawlErrorLink(successCount: Int, errorCount: Int)
    fun onCrawlFail()
    fun onCrawlTest(urls:MutableSet<String>)
}