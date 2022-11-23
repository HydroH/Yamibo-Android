package com.hydroh.yamibo.data.parser

import org.jsoup.nodes.Document

fun Document.getAvatarUrl(): String? {
    return select("img.header-tu-img")
        .first()
        ?.attr("src")
        ?.replace("small", "big")
}