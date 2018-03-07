package com.lizhidan.myandroidcrawler.ui.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.lizhidan.myandroidcrawler.R
import com.aramis.library.base.BaseActivity
import com.lizhidan.myandroidcrawler.bean.YellowResIds
import com.lizhidan.myandroidcrawler.bean.YellowResListBean
import com.lizhidan.myandroidcrawler.ui.adapter.YellowResListAdapter
import kotlinx.android.synthetic.main.activity_yellow_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * YellowListActivity
 * Created by lizhidan on 2018/3/6.
 */
class YellowListActivity : BaseActivity() {

    private val dataList = mutableListOf<YellowResListBean>()
    private val adapter = YellowResListAdapter(dataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yellow_list)
        initView()
        initData()
        setListener()
    }

    private fun setListener() {
//        recycler_yellow_list.
    }

    private fun initData() {
        dataList.add(YellowResListBean(YellowResIds.RedFirst, "源1", "123"))
        dataList.add(YellowResListBean(YellowResIds.RedSecond, "源2", "123"))
        dataList.add(YellowResListBean(YellowResIds.RedThird, "源3", "123", "需要翻墙"))
        adapter.notifyDataSetChanged()
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_yellow_list.layoutManager = linearLayoutManager
        recycler_yellow_list.adapter = adapter
        recycler_yellow_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener { view, position ->
                        toast(position.toString())
            startActivity<YellowActivity>("data" to dataList[position])
        }

    }
}