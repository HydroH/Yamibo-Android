package com.hydroh.yamibo.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.toMutableStateList
import com.hydroh.yamibo.data.network.OkHttpSingleton
import com.hydroh.yamibo.data.network.UrlGetter
import com.hydroh.yamibo.data.network.syncFormPost
import com.hydroh.yamibo.data.network.syncGet
import com.hydroh.yamibo.data.parser.getAvatarUrl
import com.hydroh.yamibo.data.parser.getSectionGroups
import com.hydroh.yamibo.ui.home.HomeUIState
import com.hydroh.yamibo.util.LoginException
import com.hydroh.yamibo.util.NetworkException
import com.hydroh.yamibo.util.parse
import com.hydroh.yamibo.util.removeScripts
import okhttp3.FormBody
import java.nio.charset.Charset

object DataProvider {
    @JvmStatic
    fun init(context: Context) {
        OkHttpSingleton.init(context)
    }

    @JvmStatic
    fun login(username: String, password: String) {
        var doc = syncGet(UrlGetter.getLoginFormUrl()).parse()
        val rawHtml = doc.html()
        var index = rawHtml.indexOf("name=\"formhash\"")
        if (index == -1) throw NetworkException()

        val formHash = rawHtml.substring(index + 23, index + 31)
        index = rawHtml.indexOf("loginform_")
        if (index == -1) throw NetworkException()
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
        doc = syncFormPost(UrlGetter.getLoginRequestUrl(loginHash), formBody).parse()
        Log.d(ContentValues.TAG, "login: ${doc.html()}")
        if (!doc.outerHtml().contains("欢迎")) {
            val message = doc.select("p").first()?.text() ?: doc.text().removeScripts()
            throw LoginException(message)
        }
    }

    @JvmStatic
    fun getHomeData(): HomeUIState {
        val doc = syncGet(UrlGetter.getDefaultUrl()).parse()
        return HomeUIState(
            sectionGroups = doc.getSectionGroups().toMutableStateList(),
            avatarUrl = doc.getAvatarUrl(),
        )
    }
}