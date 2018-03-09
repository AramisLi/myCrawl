package com.aramis.library.base

import android.app.Activity
import android.text.TextUtils
import com.aramis.library.http.ArHttpUtils
import com.aramis.library.http.custom.BaseBeanKotlin
import com.aramis.library.http.interfaces.DefaultHttpListener
import com.aramis.library.http.interfaces.DefaultListHttpListener
import com.aramis.library.http.interfaces.DefaultObjectHttpListener
import com.google.gson.Gson
import com.kymjs.rxvolley.client.HttpCallback
import com.kymjs.rxvolley.client.HttpParams

/**
 * BasePresenter
 * Created by Aramis on 2017/4/26.
 */

open class BasePresenter<T : BaseView>( var mView: T?) {
    companion object {
        val ERROR_DATA = "获取数据失败"
        val LOCAL_ERROR_STR = "获取数据失败"
        val LOCAL_ERROR_CODE = 1004
    }

    protected var activity: Activity? = null
    protected val TAG = "===" + javaClass.simpleName + "==="

    init {
        if (mView is Activity) {
            activity = mView as Activity?
        }
    }

    var onDispatchView: (() -> Unit)? = null


    open fun dispatchView() {
        onDispatchView?.invoke()
        mView = null
    }

    //网络请求json
    protected fun addJsonRequest(url: String, httpParams: HttpParams, httpCallback: HttpCallback) {
        ArHttpUtils.jsonPost(url, httpParams, httpCallback)
    }

    protected fun addJsonRequestByAr(url: String, httpParams: HttpParams, httpCallback: DefaultHttpListener<*>) {
        ArHttpUtils.jsonPost(url, httpParams, httpCallback)
    }

    //上传单个图片
//    protected fun uploadSinglePic(filePath: String, params: Map<String, String>?, onSuccess: (result: UploadPicBeanOuter) -> Unit,
//                                  onFail: (errorCode: Int, errorMsg: String) -> Unit) {
//        val file = File(filePath)
//        if (file.exists()) {
//            ArHttpUtils.uploadSingleFile("pic", File(filePath), params, getDefaultHttpObject(onSuccess, onFail))
//        } else {
//            logE("上传文件不存在")
//        }
//    }

    //上传多张图片
//    protected fun uploadMultiPics(filePath: List<String>, params: Map<String, String>?,
//                                  onSuccess: (result: MutableList<UploadPicBean>) -> Unit,
//                                  onFail: (errorCode: Int, errorMsg: String) -> Unit,
//                                  onProgressListener: ProgressListener? = null) {
//        val jsonHttpParams = getRequestDTOHttpParams(null)
//        filePath.forEachIndexed { index, path ->
//            val file = File(path)
//            if (!file.exists()) {
//                logE("文件$path 不存在")
//                return@uploadMultiPics
//            } else {
//                jsonHttpParams.put("pic$index", path)
//            }
//        }

//        ArHttpUtils.post(Protocol.upload_multi, jsonHttpParams, onProgressListener, getDefaultListHttpObject<UploadPicBean>(
//                onSuccess, { onFail.invoke(0, "网络路径获取错误") }, onFail
//        ))

//    }

    /**
     * 登录后，获取各种的验证码
     *
     * @param flag 1=绑定银行，2=修改支付密码,3=分期还款获取验证码
     */
//    protected fun getCap(mobile: String, flag: Int, httpCallback: HttpCallback) {
//        val map = HashMap<String, Any>()
//        map.put("mobile", mobile)
//        map.put("flag", flag)
//        addJsonRequest(Protocol.getCaptcha, getRequestDTOHttpParams(map), httpCallback)
//    }

    protected fun getRequestDTOHttpParams(data: Map<String, Any?>?): HttpParams =
            ArHttpUtils.getJsonHttpParams(data)


    protected fun accessNetError(errorCode: Int, errorMsg: String?): Boolean {
        var b = false
        if (errorCode == -1 || TextUtils.isEmpty(errorMsg)) {
            if (mView != null) {
                mView!!.onNetError(errorCode, errorMsg)
            }
        } else {
            b = true
        }
        return b
    }

    //动作性请求判断
    protected fun onDataNull(t: String?, onSuccessListener: (() -> Unit)? = null,
                             onFailListener: ((errorCode: Int, errorMsg: String) -> Unit)?) {
        if (t != null) {
            val beanKotlin = Gson().fromJson(t, BaseBeanKotlin::class.java)
            if (beanKotlin.status == 0) {
                onSuccessListener?.invoke()
            } else {
                onFailListener?.invoke(1004, "请求错误")
            }
        } else {
            onFailListener?.invoke(1004, "请求错误")
        }
    }

    protected fun onDataNullObject(onSuccessListener: (() -> Unit)? = null,
                                   onFailListener: ((errorCode: Int, errorMsg: String) -> Unit)? = null): HttpCallback {
        return object : HttpCallback() {
            override fun onSuccess(t: String?) {
                super.onSuccess(t)
                onDataNull(t, onSuccessListener, onFailListener)
            }

            override fun onFailure(errorNo: Int, strMsg: String?) {
                super.onFailure(errorNo, strMsg)
                onFailListener?.invoke(errorNo, strMsg ?: "请求错误")
            }
        }
    }

    protected  inline fun <reified T> getDefaultHttpObject(crossinline onSuccess: ((result: T) -> Unit),
                                                          crossinline onFail: ((errorCode: Int, errorMsg: String) -> Unit)): DefaultObjectHttpListener<T> {
        val java = T::class.java
        return object : DefaultObjectHttpListener<T>(java) {
            override fun onSuccessParsed(result: T) {
                onSuccess.invoke(result)
            }

            override fun onFailWithCode(code: Int, errorNo: Int, strMsg: String?) {
                super.onFailWithCode(code, errorNo, strMsg)
                onFail.invoke(code, strMsg ?: LOCAL_ERROR_STR)
            }

        }
    }

    protected inline fun <reified T> getDefaultListHttpObject(crossinline onSuccess: ((result: MutableList<T>) -> Unit),
                                                              crossinline onListZero: (() -> Unit),
                                                              crossinline onFail: ((errorCode: Int, errorMsg: String) -> Unit)): DefaultListHttpListener<T> {
        val java = T::class.java
        return object : DefaultListHttpListener<T>(java) {
            override fun onSuccessParsed(result: MutableList<T>?) {
                if (result != null) {
                    onSuccess.invoke(result)
                } else {
                    onFail.invoke(LOCAL_ERROR_CODE, LOCAL_ERROR_STR)
                }
            }

            override fun onListSizeZero() {
                onListZero.invoke()
            }

            override fun onFailWithCode(code: Int, errorNo: Int, strMsg: String?) {
                super.onFailWithCode(code, errorNo, strMsg)
                onFail.invoke(code, strMsg ?: LOCAL_ERROR_STR)
            }
        }
    }


    protected fun getHttpCallBack(onSuccess: ((t: String) -> Unit)? = null, onFail: ((errorCode: Int, errorMsg: String) -> Unit)? = null): HttpCallback {
        return object : HttpCallback() {
            override fun onSuccess(t: String?) {
                super.onSuccess(t)
                if (t != null) {
                    onSuccess?.invoke(t)
                } else {
                    onFail?.invoke(LOCAL_ERROR_CODE, LOCAL_ERROR_STR)
                }
            }

            override fun onFailure(errorNo: Int, strMsg: String?) {
                super.onFailure(errorNo, strMsg)
                onFail?.invoke(errorNo, strMsg ?: LOCAL_ERROR_STR)
            }
        }
    }

}
