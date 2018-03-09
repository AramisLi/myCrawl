package com.lizhidan.myandroidcrawler

import android.os.Bundle
import com.aramis.library.base.BaseActivity
import com.aramis.library.base.BasePresenter
import com.lizhidan.myandroidcrawler.bean.QiuBaiBean
import com.lizhidan.myandroidcrawler.extensions.logE
import com.lizhidan.myandroidcrawler.ui.activity.YellowActivity
import com.lizhidan.myandroidcrawler.ui.activity.YellowListActivity
import com.lizhidan.myandroidcrawler.ui.adapter.QiubaiAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() ,MainView{
    override fun getPresenter(): BasePresenter<*>? {
        return null
    }

    private val presenter=QiuBaiPresenter(this)
    private val dataList= mutableListOf<QiuBaiBean>()
    private val adapter=QiubaiAdapter(dataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setListener()
        initData()


        startActivity<YellowListActivity>()
    }

    private fun initData() {
        presenter.startCraw()
    }

    private fun setListener() {
        button_start.setOnClickListener {
            presenter.startCrawTest("http://www.ipz500.com/list/index2.html")
        }
        button_to_yellow.setOnClickListener {
            startActivity<YellowActivity>()
        }
    }

    private fun initView() {
        list_main.adapter=adapter
    }

    override fun onGetDataSuccess(list: List<QiuBaiBean>) {
        logE("获取到的数据："+list.toString())
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onGetDataFail(errorCode: Int, errorMsg: String) {
        toast(errorMsg)
    }
}
