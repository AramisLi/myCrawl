package com.lizhidan.myandroidcrawler.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lizhidan.myandroidcrawler.R
import com.lizhidan.myandroidcrawler.bean.YellowBean

/**
 * QiubaiAdapter
 * Created by lizhidan on 2018/2/27.
 */
class YellowAdapter(private val list: MutableList<YellowBean>) : BaseAdapter() {
    var onDownloadClick: ((position: Int) -> Unit)? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.list_yellow, null)
            holder = ViewHolder(view.findViewById(R.id.image_item_yellow),
                    view.findViewById(R.id.text_yellow_download),
                    view.findViewById(R.id.text_yellow_title))
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        list[position].apply {
            holder.text_yellow_title.text = title
            holder.text_yellow_download.setOnClickListener {
                onDownloadClick?.invoke(position)
            }
            val context = parent?.context
            if (context != null) {
//                logE(img)
                Glide.with(context).load(imgAddr).into(holder.image_item_yellow)
            }
        }
        return view!!
    }

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    private inner class ViewHolder(val image_item_yellow: ImageView, val text_yellow_download: TextView,
                                   val text_yellow_title: TextView)
}