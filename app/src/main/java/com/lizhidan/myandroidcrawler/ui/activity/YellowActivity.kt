package com.lizhidan.myandroidcrawler.ui.activity

import android.os.Bundle
import com.lizhidan.myandroidcrawler.R
import com.lizhidan.myandroidcrawler.base.BaseActivity
import com.lizhidan.myandroidcrawler.bean.YellowBean
import com.lizhidan.myandroidcrawler.ui.adapter.YellowAdapter
import com.lizhidan.myandroidcrawler.ui.pv.YellowPresenter
import com.lizhidan.myandroidcrawler.ui.pv.YellowView
import kotlinx.android.synthetic.main.activity_yellow.*

/**
 * YellowActivity
 * Created by lizhidan on 2018/3/2.
 */
class YellowActivity:BaseActivity(),YellowView {

    private val presenter=YellowPresenter(this)
    private val dataList= mutableListOf<YellowBean>()
    private val adapter= YellowAdapter(dataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yellow)
        initView()
        setListener()
    }

    override fun onCrawlMessage(bean: YellowBean) {
        dataList.add(bean)
        adapter.notifyDataSetChanged()
    }

    private fun initView() {
        list_yellow.adapter=adapter
    }

    private fun setListener() {
        text_yellow_start.setOnClickListener {
            presenter.startCrawl()
        }
    }
}