package com.lizhidan.myandroidcrawler.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lizhidan.myandroidcrawler.R
import com.lizhidan.myandroidcrawler.bean.QiuBaiBean

/**
 * QiubaiAdapter
 * Created by lizhidan on 2018/2/27.
 */
class QiubaiAdapter(private val list: MutableList<QiuBaiBean>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.list_main, null)
            holder = ViewHolder(view.findViewById(R.id.image_author),
                    view.findViewById(R.id.text_author_name),
                    view.findViewById(R.id.text_list_content),
                    view.findViewById(R.id.image_list_content))
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        list[position].apply {
            holder.text_author_name.text = author
            holder.text_list_content.text=content
            val context=parent?.context
            if (context!=null){
//                logE(img)
                Glide.with(context).load(img).into(holder.image_list_content)
            }
            if (context!=null){
                Glide.with(context).load(avatar).into(holder.image_author)
            }
        }
        return view!!
    }

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    private inner class ViewHolder(val image_author: ImageView, val text_author_name: TextView,
                                   val text_list_content: TextView,val image_list_content:ImageView)
}