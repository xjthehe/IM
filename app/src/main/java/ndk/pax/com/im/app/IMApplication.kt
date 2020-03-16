package ndk.pax.com.im.app

import android.app.Application
import cn.bmob.v3.Bmob
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import ndk.pax.com.im.BuildConfig

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:10:50
 *
 */

class IMApplication :Application(){

    companion object {
        lateinit var instance:IMApplication
    }
    override fun onCreate() {
        super.onCreate()

        //初始化
        instance=this

        EMClient.getInstance().init(applicationContext, EMOptions());
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);

        //Bmob
        Bmob.initialize(applicationContext,"2ee4708569a21a8922b2020ab5a365b7");
    }

}

