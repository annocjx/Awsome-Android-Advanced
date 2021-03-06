package com.prim.prim_test.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.prim.http.lib_net.PrimHttp
import cn.prim.http.lib_net.model.Response
import com.prim.prim_test.R
import com.prim.prim_test.http.modle.SearchModel
import kotlinx.android.synthetic.main.activity_base_http.*
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.anko.sdk27.coroutines.onClick
import cn.prim.http.lib_net.callback.Callback as Callback1

class BaseHttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_http)
        tv_get_request.onClick {
            get()
        }
    }

    private fun CoroutineScope.get() {
        PrimHttp.getInstance().get<SearchModel>("search")
            .params("keywords", "海阔天空")//请求参数
            .headers("test", "head")//请求头
            .repeatCount(3)//请求失败后重试的次数
            .repeatDuration(500)//请求失败后
            .tag(this)
            .enqueue(Callback())
    }

    //注意声明内部类inner 标记才能访问外部类的成员
    inner class Callback : Callback1<SearchModel>() {
        override fun onSuccess(t: SearchModel?) {
            tv_state.text = "请求网络成功"
        }

        override fun onStart() {
            tv_state.text = "开始请求网络"
        }

        override fun onError() {
            tv_state.text = "请求网络失败"
        }

        override fun onComplete() {
//            tv_state.text = "请求网络完成"
        }
    }
}
