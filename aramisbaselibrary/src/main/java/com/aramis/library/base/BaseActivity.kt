package com.aramis.library.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * BaseActivity
 * Created by lizhidan on 2018/2/27.
 */
open class BaseActivity:AppCompatActivity(),BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}