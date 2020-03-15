package ndk.pax.com.im.ui.fragment

import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import kotlin.math.log
import com.hyphenate.EMCallBack
import ndk.pax.com.im.adapter.EMCallBackAdapter
import ndk.pax.com.im.ui.activity.LoginActivity
import ndk.pax.com.im.ui.activity.MainActivity
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:10:38
 *
 */

class DynamicFragment:BaseFragment(){
    override fun getLayoutId(): Int = R.layout.fragment_dynamic

    override fun init() {
        super.init()
        headerTitle.text=getString(R.string.dynamic)

        val logoutString=String.format(getString(R.string.logout), EMClient.getInstance().currentUser)
        logout.text= logoutString

        //退出登录监听
        logout.setOnClickListener {
            logout();
        }
    }

    fun logout(){
        EMClient.getInstance().logout(true, object : EMCallBackAdapter() {
            override fun onSuccess() {
                context?.runOnUiThread {
                    context?.toast(R.string.logout_success)
                    context?.startActivity<LoginActivity>()
                    activity?.finish();
                }
            }
            override fun onError(p0: Int, p1: String?) {
                //切换主线程
                context?.runOnUiThread {
                    toast(R.string.logout_failed)
                }
            }
        })

    }






}