package ndk.pax.com.im.ui.activity

import kotlinx.android.synthetic.main.activity_register.*
import ndk.pax.com.im.R
import ndk.pax.com.im.contract.RegisterContract
import ndk.pax.com.im.presenter.RegisterPresenter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:19:26
 *
 */

class RegisterActivity : BaseActivity(), RegisterContract.View {
    override fun onUserExit() {
        dismissProgress();
        toast(R.string.user_already_exist);
    }

    val presenter by lazy { RegisterPresenter(this) }

    override fun onUserNameError() {
        userName.setError(getString(R.string.user_name_error));
    }

    override fun onPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    override fun onConfirmPasswordError() {
        confirmPassword.setError(getString(R.string.confirm_password_error));
    }

    override fun onStartRegister() {
        //弹出进度条
        showProgress(getString(R.string.registering))
    }

    override fun onRegisterSuccees() {
        dismissProgress();
        toast(R.string.register_success)
        startActivity<LoginActivity>()
    }

    override fun onRegisterFaile() {
        dismissProgress();
        //弹出toast
        toast(getString(R.string.register_failed));//anko库封装的
    }

    override fun getLayoutResId(): Int = R.layout.activity_register;
    override fun init() {
        super.init()
        register.setOnClickListener {
            register();
        }
    }

    fun register(){
       // hideSoftKeyboard();
        val userName=userName.text.trim().toString();
        val passWord=password.text.trim().toString();
        val confirmPassWord=password.text.trim().toString();
        presenter.register(userName,passWord,confirmPassWord);
    }
}