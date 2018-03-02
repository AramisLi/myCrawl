package com.lizhidan.myandroidcrawler.ui.dialog

import android.app.Dialog
import android.content.Context
import com.lizhidan.myandroidcrawler.R

/**
 * YellowDialog
 * Created by lizhidan on 2018/3/2.
 */
class YellowDialog(context: Context) : BaseDialog {


    private var dialog: Dialog? = null

    init {
        dialog = Dialog(context, R.style.new_custom_dialog)
    }

    override fun show() {
        this.dialog?.show()
    }

    override fun dismiss() {
        this.dialog?.dismiss()
    }

    override fun isShowing(): Boolean {
        return dialog?.isShowing ?: false
    }
}