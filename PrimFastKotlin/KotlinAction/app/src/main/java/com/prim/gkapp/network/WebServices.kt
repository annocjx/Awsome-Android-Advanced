package com.prim.gkapp.network

import com.prim.gkapp.network.interceptors.AcceptInterceptor
import com.prim.gkapp.network.interceptors.AuthInterceptor
import com.prim.lib_base.AppContext
import com.prim.lib_base.utils.ensureDir
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @desc 请求网络
 * @author prim
 * @time 2019-05-29 - 07:30
 * @version 1.0.0
 */

private const val BASE_URL = "https://api.github.com"

//lazy 延迟初始化 再用到到时候才会初始化

//缓存
private val cacheFile by lazy {
    File(AppContext.cacheDir, "webApi").apply { ensureDir() }
}

val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(//自定义适配器 内部封装线程转换
            RxJava2CallAdapterFactory2.createWithScheduler(
                Schedulers.io(),
                AndroidSchedulers.mainThread()
            )
        )
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())
                .addInterceptor(AcceptInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                .build()
        )
        .baseUrl(BASE_URL)
        .build()
}