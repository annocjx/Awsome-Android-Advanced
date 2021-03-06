package com.prim.lib_base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.prim.lib_base.mvp.IMvpView
import com.prim.lib_base.mvp.IPresenter
import com.prim.lib_base.mvp.impl.BasePresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @desc Fragment的基类
 * @author prim
 * @time 2019-05-28 - 06:11
 * @version 1.0.0
 */
abstract class BaseFragment<out P : BasePresenter<BaseFragment<P>>> : IMvpView<P>, Fragment() {
    final override val presenter: P

    //初始化 Presenter
    init {
        presenter = createPresenterKt()
        presenter.view = this
    }

    /**
     * 创建presenter 通过java的反射
     * 这样有一个坏处 不能去写其他的构造方法了，好处就是自动创建Presenter的实例
     */
    fun createPresenterKt(): P {
        //kotlin 1.3 之前的版本 buildSequence
        //sequence
        sequence<Type> {
            var thisClass: Class<*> = this@BaseFragment.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType//过滤type 找到ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()//获取泛型的第一个
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initContext(activity)
        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onRestart() {
        presenter.onRestart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}