package ndk.pax.com.im

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.util.LogPrinter
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import ndk.pax.com.im.contract.LoginContract
import ndk.pax.com.im.presenter.LoginPresenter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.jar.Manifest

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/13:18:15
 *
 */

class LoginActivity:BaseActivity(),LoginContract.View{

    val presenter=LoginPresenter(this);

    override fun onUserNameError() {
        userName.setError(getString(R.string.user_name_error));
    }

    override fun onPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    override fun onStartLogin() {
        //弹出进度条
        showProgress(getString(R.string.logging))
    }

    override fun onLoginSuccees() {
        //隐藏进度条
        dismissProgress();
        //进入主界面
        startActivity<MainActivity>();
        //退出loging
        finish();
    }

    override fun onLoginFaile() {
        //隐藏进度条
        dismissProgress();
        //弹出toast
        toast(getString(R.string.login_failed));//anko库封装的
    }

    override fun getLayoutResId(): Int =R.layout.activity_login;

    override fun init() {
        super.init()
        //新用户注册
        newUser.setOnClickListener {
                startActivity<RegisterActivity>();
        }
        login.setOnClickListener{login()}
        password.setOnEditorActionListener(object :TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                login();
                return true;
            }
        }
        )
    }


    fun login(){
        hideSoftKeyboard();
        if(hasWriteExternalStoragePermission()){
            val userNameString=userName.text.trim().toString();
            val passwordString=password.text.trim().toString();
            presenter.login(userNameString,passwordString);
        }else{
            applyWriteExternalStoragePermission();
        }

    }
    //请求权限
    private fun applyWriteExternalStoragePermission() {
        val permissions= arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions(this,permissions,0);
        //多次拒绝后，勾选不提示框，下面直接走else
    }

    //requestPermissions 回调函数
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            login();
        }else toast(R.string.permission_denied);
    }

    //检查磁盘是否有权限
    private fun hasWriteExternalStoragePermission(): Boolean {
        val result=ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result==PackageManager.PERMISSION_GRANTED;
    }
}


