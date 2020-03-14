package ndk.pax.com.im

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {
     val progressDialog by lazy {
         ProgressDialog(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId());
        init();
    }


    open fun init() {
        //初始化一些公共功能，比如摇一摇，子类覆盖该方法，完成自己初始化
    }

    abstract fun getLayoutResId():Int;

    fun showProgress(message:String){
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    fun dismissProgress(){
        progressDialog.dismiss();
    }
}
