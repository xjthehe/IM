package ndk.pax.com.im.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/13:9:23
 *
 */

abstract class BaseFragment:Fragment(){


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(getLayoutId(),null)
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
         inflater.inflate(getLayoutId(),null);


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init();
    }

    open fun init() {
    //初始化一些公共功能，子类也可以复写该方法，完成自己初始化。
    }

    abstract fun getLayoutId(): Int

}