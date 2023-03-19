package com.hydroh.yamibo.data.network

import android.content.Context
import coil.ImageLoader
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient

object HttpSingleton {
    private const val UA =
        "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0"
    lateinit var client: OkHttpClient
    lateinit var imageLoader: ImageLoader

    fun init(context: Context) {
        val cookieJar: ClearableCookieJar = PersistentCookieJar(
            SetCookieCache(), SharedPrefsCookiePersistor(context)
        )
        client = OkHttpClient().newBuilder()
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader("User-Agent", UA)
                    .build()
                chain.proceed(request)
            }
            .build()

        imageLoader = ImageLoader.Builder(context)
            .okHttpClient(client)
            .crossfade(true)
            .build()
    }
}
