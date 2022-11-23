package com.hydroh.yamibo.util

import okhttp3.Response
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun Response.parse(): Document {
    if (!isSuccessful || body == null) throw NetworkException()
    return Jsoup.parse(body!!.string())
}
