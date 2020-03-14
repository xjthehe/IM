package ndk.pax.com.im

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager

abstract class BaseActivity : AppCompatActivity() {
    val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }


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

    fun hideSoftKeyboard(){
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
    }
}
