package ndk.pax.com.im.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.activity_main.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.EMMessageListenerAdapter
import ndk.pax.com.im.factory.FragmentFactory
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int=R.layout.activity_main;
    //底部导航条位置更新
    val messageListener=object :EMMessageListenerAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            super.onMessageReceived(p0)
            runOnUiThread {
                //收到消息就更新
                updateBottomBar()
            }
        }
    }
    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction();
            beginTransaction.replace(R.id.fragment_frame, FragmentFactory.instance.getInstatance(tabId)!!);
            beginTransaction.commit();//别忘了提交
//            FragmentFactory.instance.getInstatance(tabId)?.let { beginTransaction.replace(R.id.fragment_frame, it) };
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        EMClient.getInstance().addConnectionListener(object : EMConnectionListener{
            override fun onConnected() {

            }

            override fun onDisconnected(p0: Int) {
                //发生在多设备登录，跳转登录界面
                if(p0== EMError.USER_LOGIN_ANOTHER_DEVICE){
                        toast(R.string.user_login_another_device)
                        startActivity<LoginActivity>()
                        finish()
                    }
            }
        })
    }

    //bootBar显示未读会话所有消息
    override fun onResume() {
        super.onResume()
        updateBottomBar()
    }

    private fun updateBottomBar() {
        val tab = bottomBar.getTabWithId(R.id.tab_conversation)
        tab.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy(){
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

}

