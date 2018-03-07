package com.aramis.library.base

/**
 * BasePresenter
 * Created by lizhidan on 2018/2/27.
 */
open class BasePresenter<T : BaseView>(protected var mView: T?) {

    fun attachView() {
    }

    fun disattachView() {
        this.mView = null
    }
}