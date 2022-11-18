package com.hydroh.yamibo.network

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import java.nio.charset.Charset

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

fun syncFormPost(url: String, body: FormBody): Response {
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    return OkHttpSingleton.client.newCall(request).execute()
}

fun login(username: String, password: String): Boolean {
    var response = syncGet(UrlGetter.getLoginFormUrl())
    if (!response.isSuccessful) return false

    var doc = Jsoup.parse(response.body?.string() ?: "")
    val rawHtml = doc.html()
    var index = rawHtml.indexOf("name=\"formhash\"")
    if (index == -1) {
        return false
    }
    Log.d(TAG, "login: ${doc.html()}")

    val formHash = rawHtml.substring(index + 23, index + 31)
    index = rawHtml.indexOf("loginform_")
    if (index == -1) {
        return false
    }
    val loginHash = rawHtml.substring(index + 10, index + 15)

    val formBody = FormBody.Builder(Charset.forName("GBK"))
        .add("answer", "")
        .add("cookietime", "2592000")
        .add("formhash", formHash)
        .add("loginfield", "username")
        .add("username", username)
        .add("password", password)
        .add("questionid", "0")
        .add("referer", UrlGetter.getDefaultUrl())
        .build()
    response = syncFormPost(UrlGetter.getLoginRequestUrl(loginHash), formBody)
    if (!response.isSuccessful) return false
    doc = Jsoup.parse(response.body?.string() ?: "")
    Log.d(TAG, "login: ${doc.html()}")
    if (doc.outerHtml().contains("欢迎")) return true
    return false
}