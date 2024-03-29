package com.hydroh.yamibo.data.network

import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response

fun asyncGet(url: String, callback: Callback) {
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    HttpSingleton.client.newCall(request)
        .enqueue(callback)
}

fun syncGet(url: String): Response {
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    return HttpSingleton.client.newCall(request).execute()
}

fun syncFormPost(url: String, body: FormBody): Response {
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    return HttpSingleton.client.newCall(request).execute()
}
