package ndk.pax.com.im.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import cn.bmob.v3.Bmob
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.hyphenate.chat.EMTextMessageBody
import ndk.pax.com.im.BuildConfig
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.EMMessageListenerAdapter
import ndk.pax.com.im.adapter.MessageListAdapter
import ndk.pax.com.im.ui.activity.ChatActivity
import java.io.FileDescriptor

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

       val soundPool=SoundPool(2,AudioManager.STREAM_MUSIC,0)

        //id
       val duan by lazy {
           soundPool.load(instance, R.raw.duan,0)
       }

        val yulu by lazy {
            soundPool.load(instance, R.raw.yulu,0)
        }
    val messageListener=object :EMMessageListenerAdapter(){
        override fun onMessageRead(p0: MutableList<EMMessage>?) {
            //如果在前台 播放短声音
            if(isPreground()){
                soundPool.play(duan,1f,1f,0,0,1f)
            }else{
                soundPool.play(yulu,1f,1f,0,0,1f)
                showNotification(p0)
            }
            //后台播放长声音
        }

    }
    //后台收到消息 弹出notification
    private fun showNotification(p0: MutableList<EMMessage>?) {
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        p0?.forEach {
                var contentText=getString(R.string.no_text_message)
                if(it.type==EMMessage.Type.TXT){
                    contentText=(it.body as EMTextMessageBody).message
                }

                val intent= Intent(this,ChatActivity::class.java)

//                val peddingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

                  //点击进入聊天界面后,点击返回不会直接退出,将activity添加到栈里面
                  val taskStackBuilder=TaskStackBuilder.create(this).addParentStack(ChatActivity::class.java).addNextIntent(intent)
                  val peddingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)

                val notifivation=Notification.Builder(this)
                        .setContentTitle(getString(R.string.receive_new_message))
                        .setContentText(contentText)
                        .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.avatar2))
                        .setSmallIcon(R.mipmap.ic_contact)
                        .setContentIntent(peddingIntent)
                        .setAutoCancel(true)
                        .notification

                 notificationManager.notify(0,notifivation)
        }
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

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    //app运行在前台
    private fun isPreground():Boolean{
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (runningAppProcess in activityManager.runningAppProcesses){
            if(runningAppProcess.processName==packageName){
                return runningAppProcess.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false



    }
}

