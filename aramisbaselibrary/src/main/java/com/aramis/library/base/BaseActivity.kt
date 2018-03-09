package com.aramis.library.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.aramis.library.R
import com.aramis.library.aramis.WebBrowserActivity
import com.aramis.library.component.dialog.BeforeLoadingDialog
import com.aramis.library.ui.dialog.LoadingDialog
import com.aramis.library.utils.ScreenUtils
import com.aramis.library.utils.StatusBarUtil
import rx.Subscription


/**
 * BaseActivity
 * Created by Aramis on 2017/4/26.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {
    val TAG = "===" + javaClass.simpleName + "==="
    protected var screenWidth: Int = 0
    protected var screenHeight: Int = 0

    private var loadingDialog: LoadingDialog? = null
    private var isNeedLoadingDialog = true
    private var isNeedAutoBackView = true

    private val bundleSubscribe: Subscription? = null
    protected var useDefaultTitleColor = true

    protected val titleMiddleTextView: TextView
        get() = findViewById<View>(R.id.text_toolbar_middle) as TextView

    private val presenter:BasePresenter<*>?=null
    abstract fun getPresenter():BasePresenter<*>?

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.StatusBarLightMode(this)
        if (useDefaultTitleColor) {
            ScreenUtils.setDefaultTitleColor(this)
        }
        screenWidth = resources.displayMetrics.widthPixels
        screenHeight = resources.displayMetrics.heightPixels
        loadingDialog = BeforeLoadingDialog(this)

        //        bundleSubscribe = ArBus.getDefault().take(Bundle.class).map(new Func1<Bundle, Object>() {
        //            @Override
        //            public Object call(Bundle bundle) {
        //                if (bundle.getBoolean(ArHttpConfig.TOKEN_OVERDUE_BUS_KEY, false)) {
        //                    ToastUtils.showToast(mActivity, bundle.getString(ArHttpConfig.TOKEN_OVERDUE_ERROR_KEY, ""));
        //                    //强制登录
        //                    constraintLogin(true);
        //                }
        //                if (bundle.getBoolean(RxBusKeys.INSTANCE.getLoginSuccess(), false)) {
        //                    onTokenUpdate();
        //                }
        //                return bundle;
        //            }
        //        }).subscribe();
        //友盟统计应用启动数据
        //        PushAgent.getInstance(this).onAppStart();
    }

    protected fun toSystemContacts() {
        //        Uri uri = Uri.parse("content://contacts/people");
        //        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, RequestCode_Contacts)
    }

    override fun onResume() {
        super.onResume()
        tryToAddBackClick()
        //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。参数为页面名称，可自定义)
        //        MobclickAgent.onPageStart("BaseActivity");
        // 友盟统计，所有Activity中添加，父类添加后子类不用重复添加
        //        MobclickAgent.onResume(this);

    }

    override fun onPause() {
        super.onPause()
        //        MobclickAgent.onPageEnd("BaseActivity"); // [(仅有Activity的应用中SDK自动调用,不需要单独写)保证onPageEnd在onPause之前调用,因为onPause中会保存信息。参数页面名称,可自定义]
        // 友盟统计，所有Activity中添加，父类添加后子类不用重复添加
        //        MobclickAgent.onPause(this);
    }

    //默认返回键
    private fun tryToAddBackClick() {
        val backView = findViewById<View>(R.id.image_toolbar_back)
        if (isNeedAutoBackView && backView != null) {
            backView.setOnClickListener { onBackPressed() }
        }
    }

    //设置是否需要默认返回
    fun setNeedAutoBackView(isNeedAutoBackView: Boolean) {
        this.isNeedAutoBackView = isNeedAutoBackView
    }

    //设置toolbar右边的textView
    protected fun setTitleRightText(text: String?, onClickListener: View.OnClickListener) {
        val text_toolbar_right = findViewById<View>(R.id.text_toolbar_right) as TextView
        if (text_toolbar_right != null) {
            text_toolbar_right.visibility = View.VISIBLE
            if (!TextUtils.isEmpty(text)) text_toolbar_right.text = text
            text_toolbar_right.setOnClickListener(onClickListener)
        }
    }

    fun setTitleRightText(onClickListener: View.OnClickListener) {
        setTitleRightText(null, onClickListener)
    }

    //设置toolbar中间的textView
    protected fun setTitleMiddleText(text: String) {
        setTitleMiddleText(View.VISIBLE, text)
    }

    //设置toolbar中间的textView
    protected fun setTitleMiddleText(visibility: Int, text: String) {
        val text_toolbar_middle = findViewById<View>(R.id.text_toolbar_middle) as TextView
        if (text_toolbar_middle != null) {
            text_toolbar_middle.visibility = visibility
            text_toolbar_middle.text = text
        }
    }

    //设置title line显示
    protected fun setTitleLineVisible(visible: Int) {
        val titleView = findViewById<View>(R.id.line_toolbar)
        if (titleView != null) {
            titleView.visibility = visible
        }
    }

    protected fun setTitleLeftNavigator(onClickListener: View.OnClickListener) {
        setTitleLeftNavigator(-1, onClickListener)
    }

    protected fun setTitleLeftNavigator(resId: Int, onClickListener: View.OnClickListener) {
        setTitleLeftNavigator(View.VISIBLE, resId, onClickListener)
    }

    protected fun setTitleLeftNavigator(visibility: Int, resId: Int, onClickListener: View.OnClickListener) {
        val image_toolbar_left = findViewById<View>(R.id.image_toolbar_left) as ImageView
        findViewById<View>(R.id.image_toolbar_back).visibility = View.GONE
        if (image_toolbar_left != null) {
            if (visibility == View.VISIBLE) {
                if (resId > 0) image_toolbar_left.setImageResource(resId)
                image_toolbar_left.setOnClickListener(onClickListener)
            } else {
                image_toolbar_left.visibility = visibility
            }
        }
    }

    protected fun setTitleRightNavigator(resId: Int, onClickListener: View.OnClickListener) {
        val image_toolbar_right = findViewById<View>(R.id.image_toolbar_right) as ImageView
        if (image_toolbar_right != null) {
            image_toolbar_right.visibility = View.VISIBLE
            image_toolbar_right.setImageResource(resId)
            image_toolbar_right.setOnClickListener(onClickListener)
        }
    }

    //隐藏键盘
    protected fun hideKeyboard() {
        (getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }

    protected fun toOtherActivity(clazz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clazz)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
    }

    //    protected boolean isLogin() {
    //        return isLogin(true);
    //    }

    //    protected boolean isLogin(boolean toActivity) {
    //        boolean login = BunnySP.Companion.isLogin();
    //        if (toActivity && !login) {
    //            LogUtils.e(TAG, "启动登录activity");
    //            toOtherActivity(LoginSingleActivity.class, null);
    //        }
    //        return login;
    //    }

    //强制登录
    //    protected boolean constraintLogin(boolean isCompulsive) {
    //        toOtherActivity(LoginSingleActivity.class, null);
    //        BunnySP.Companion.putIsLogin(false);
    //        return false;
    //    }

    //toWebActivity
    protected fun toWebActivity(webUrl: String, title: String) {
        val intent = Intent(this, WebBrowserActivity::class.java)
        intent.putExtra(WebBrowserActivity.WEB_URL, webUrl)
        intent.putExtra(WebBrowserActivity.WEB_TITLE, title)
        startActivity(intent)
    }

    protected fun needLoadingDialog(isNeed: Boolean) {
        this.isNeedLoadingDialog = isNeed
    }

    override fun getLoadingDialog(): LoadingDialog? {
        return if (isNeedLoadingDialog) loadingDialog else null
    }

    override fun onNetError(errorCode: Int, errorMessage: String) {
        var errorMessage = errorMessage
        if (errorCode == -1) {
            errorMessage = "无网络，请打开网络或者移动数据"
        } else if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = "网络错误：$errorCode"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun onTokenUpdate() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.dispatchView()
        bundleSubscribe!!.unsubscribe()
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("", true)
        setResult(0, intent)
        super.onBackPressed()
    }

    companion object {
        protected val RequestCode_Contacts = 32
    }
}
