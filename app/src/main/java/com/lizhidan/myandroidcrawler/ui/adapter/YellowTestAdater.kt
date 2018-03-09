package com.lizhidan.myandroidcrawler.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.lizhidan.myandroidcrawler.R
import com.lizhidan.myandroidcrawler.bean.YellowBean

/**
 * YellowTestAdapter
 * Created by lizhidan on 2018/3/8.
 */
class YellowTestAdapter (private val list: MutableList<String>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val holder: ViewHolder
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.list_yellow_test, null)
                holder = ViewHolder(view.findViewById(R.id.text_yellow_link))
                view.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }
            list[position].apply {
                holder.text_yellow_link.text=this
            }
            return view!!
        }

        override fun getItem(position: Int): Any = list[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = list.size

        private inner class ViewHolder(val text_yellow_link:TextView)
}