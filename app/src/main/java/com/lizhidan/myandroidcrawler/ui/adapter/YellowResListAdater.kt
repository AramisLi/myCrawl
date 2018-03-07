package com.lizhidan.myandroidcrawler.ui.adapter

import android.widget.TextView
import com.lizhidan.myandroidcrawler.R
import com.aramis.library.ui.recycler.BaseRecyclerViewAdapter
import com.aramis.library.ui.recycler.BaseRecyclerViewHolder
import com.lizhidan.myandroidcrawler.bean.YellowResListBean

/**
 * YellowResListAdapter
 * Created by lizhidan on 2018/3/6.
 */
class YellowResListAdapter(list:MutableList<YellowResListBean>): BaseRecyclerViewAdapter<YellowResListBean>(R.layout.list_yellow_source,list){
    override fun convert(holder: BaseRecyclerViewHolder, t: YellowResListBean) {
        holder.getView<TextView>(R.id.text_source_name).text=t.name
        holder.getView<TextView>(R.id.text_source_remark).text=t.remark
    }

}

