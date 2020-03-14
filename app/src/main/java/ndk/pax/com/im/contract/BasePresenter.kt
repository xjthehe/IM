package ndk.pax.com.im.contract;

import android.os.Handler
import android.os.Looper

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/13:16:50
 */

interface BasePresenter {
    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }
    fun uiThread(f:()->Unit){
        handler.post(object :Runnable{
            override fun run() {
                f();
            }
        })
    }

}
