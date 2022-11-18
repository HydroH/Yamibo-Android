package com.hydroh.yamibo.network

import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response

fun asyncGet(url: String, callback: Callback) {
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    OkHttpSingleton.client.newCall(request)
        .enqueue(callback)
}

fun syncGet(url: String): Response {
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    return OkHttpSingleton.client.newCall(request).execute()
}
