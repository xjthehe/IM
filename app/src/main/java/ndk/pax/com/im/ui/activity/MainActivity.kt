package ndk.pax.com.im.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ndk.pax.com.im.R
import ndk.pax.com.im.factory.FragmentFactory

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int=R.layout.activity_main;

    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction();
            FragmentFactory.instance.getInstatance(tabId)?.let { beginTransaction.replace(R.id.fragment_frame, it) };
        }
    }
}

