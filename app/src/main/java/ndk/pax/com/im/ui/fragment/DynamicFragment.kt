package ndk.pax.com.im.ui.fragment

import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import kotlin.math.log

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


    }
}