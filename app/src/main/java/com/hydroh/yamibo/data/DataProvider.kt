package com.hydroh.yamibo.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.hydroh.yamibo.data.network.OkHttpSingleton
import com.hydroh.yamibo.data.network.UrlGetter
import com.hydroh.yamibo.data.network.syncFormPost
import com.hydroh.yamibo.data.network.syncGet
import com.hydroh.yamibo.model.SectionGroup
import com.hydroh.yamibo.util.Consts
import com.hydroh.yamibo.util.removeScripts
import okhttp3.FormBody
import org.jsoup.Jsoup
import java.nio.charset.Charset

object DataProvider {
    @JvmStatic
    fun init(context: Context) {
        // TODO: Fix R.string problems
        Consts.init(context)
        OkHttpSingleton.init(context)
    }

    @JvmStatic
    fun login(username: String, password: String) {
        var response = syncGet(UrlGetter.getLoginFormUrl())
        if (!response.isSuccessful) throw Exception(Consts.ERROR_NETWORK)

        var doc = Jsoup.parse(response.body?.string() ?: "")
        val rawHtml = doc.html()
        var index = rawHtml.indexOf("name=\"formhash\"")
        if (index == -1) throw Exception(Consts.ERROR_NETWORK)

        val formHash = rawHtml.substring(index + 23, index + 31)
        index = rawHtml.indexOf("loginform_")
        if (index == -1) throw Exception(Consts.ERROR_NETWORK)
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
        if (!response.isSuccessful) throw Exception(Consts.ERROR_NETWORK)
        doc = Jsoup.parse(response.body?.string() ?: "")
        Log.d(ContentValues.TAG, "login: ${doc.html()}")
        if (!doc.outerHtml().contains("欢迎")) {
            val message = doc.select("p").first()?.text() ?: doc.text().removeScripts()
            throw Exception(message)
        }
    }

    @JvmStatic
    fun getHomeItemList(): ArrayList<SectionGroup> {
        TODO()
    }
}