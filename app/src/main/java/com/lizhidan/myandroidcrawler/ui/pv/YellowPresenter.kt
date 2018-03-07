package com.lizhidan.myandroidcrawler.ui.pv

import android.os.Handler
import com.aramis.library.base.BasePresenter
import com.aramis.library.base.BaseView
import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.bean.YellowResListBean
import com.lizhidan.myandroidcrawler.crawler.YellowCrawlExecuter

/**
 * YellowPresenter
 * Created by lizhidan on 2018/3/2.
 */
class YellowPresenter(view: YellowView) : BasePresenter<YellowView>(view) {
    private val executer = YellowCrawlExecuter()
    private val handler = Handler(Handler.Callback {
        when (it.what) {
            YellowCrawlExecuter.HANDLE_SEND -> {
                mView?.onCrawlMessage(it.obj as YellowBean)
            }
        }
        false
    })

    fun startCrawl(bean: YellowResListBean) {
        executer.executeCrawl(bean, handler)
    }
}

interface YellowView : BaseView {
    fun onCrawlMessage(bean: YellowBean)
}