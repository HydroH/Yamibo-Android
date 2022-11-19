package com.hydroh.yamibo.data.parser

import com.hydroh.yamibo.model.SectionGroup
import org.jsoup.Jsoup

fun parseHomeList(html: String): ArrayList<SectionGroup> {
    val doc = Jsoup.parse(html)
    TODO()
}
