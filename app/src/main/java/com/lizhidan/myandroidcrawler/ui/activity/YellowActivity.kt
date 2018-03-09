package com.lizhidan.myandroidcrawler.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.aramis.library.base.BaseActivity
import com.aramis.library.base.BasePresenter
import com.lizhidan.myandroidcrawler.R
import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.bean.YellowResListBean
import com.lizhidan.myandroidcrawler.ui.adapter.YellowAdapter
import com.lizhidan.myandroidcrawler.ui.pv.YellowPresenter
import com.lizhidan.myandroidcrawler.ui.pv.YellowView
import kotlinx.android.synthetic.main.activity_yellow.*
import kotlinx.android.synthetic.main.view_title_yellow.*

/**
 * YellowActivity
 * Created by lizhidan on 2018/3/2.
 */
class YellowActivity : BaseActivity(), YellowView {
    override fun getPresenter(): BasePresenter<*> = presenter

    override fun onCrawlTest(urls: MutableSet<String>) {
//        val datas= mutableListOf<String>()
//        datas.addAll(urls)
//        list_yellow.adapter= YellowTestAdapter(datas)
    }

    override fun onCrawlErrorLink(successCount: Int, errorCount: Int) {
        formatTitle("获取中 成功:$successCount,失败:$errorCount", true)
    }

    override fun onCrawlFail() {
        formatTitle("访问失败", false)
    }

    override fun onCrawlComplete(successCount: Int, errorCount: Int) {
        //爬取完成
        formatTitle("完成 成功:$successCount,失败:$errorCount", false)
    }

    private fun formatTitle(msg: String, progressVisibility: Boolean) {
        text_title_count.text = msg
        progress_title.visibility = if (progressVisibility) View.VISIBLE else View.GONE
    }

    private val presenter = YellowPresenter(this)
    private val dataList = mutableListOf<YellowBean>()
    private val adapter = YellowAdapter(dataList)

    private var yellowResListBean: YellowResListBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yellow)
        getDataFromIntent()
        initView()
        setListener()

        initData()
    }

    private fun initData() {
        //开始爬取
        yellowResListBean?.apply {
            formatTitle("获取中", true)
            presenter.startCrawl(this)
        }
    }

    private fun getDataFromIntent() {
        yellowResListBean = intent.getSerializableExtra("data") as? YellowResListBean
    }

    override fun onCrawlMessage(bean: YellowBean, successCount: Int, errorCount: Int) {
        formatTitle("获取中 成功:$successCount,失败:$errorCount", true)
        dataList.add(bean)
        adapter.notifyDataSetChanged()
    }

    private fun initView() {
        list_yellow.adapter = adapter
    }

    private fun setListener() {
        text_yellow_start.setOnClickListener {
            presenter.startCrawlTest("http://www.ipz500.com",yellowResListBean!!.source)
        }

        list_yellow.setOnItemClickListener { parent, view, position, id ->
            val url=view.findViewById<TextView>(R.id.text_yellow_link).text.toString()
            presenter.startCrawlTest(url,yellowResListBean!!.source)
        }

    }
}