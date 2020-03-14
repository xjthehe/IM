package ndk.pax.com.im

import android.util.LogPrinter
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import ndk.pax.com.im.contract.LoginContract
import ndk.pax.com.im.presenter.LoginPresenter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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
        val userNameString=userName.text.trim().toString();
        val passwordString=password.text.trim().toString();
        presenter.login(userNameString,passwordString);
    }
}


