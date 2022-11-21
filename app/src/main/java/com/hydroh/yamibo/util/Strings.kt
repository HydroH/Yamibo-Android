package com.hydroh.yamibo.util

import java.util.regex.Pattern

fun String.removeScripts(): String {
    val p = Pattern.compile(
        "<script[^>]*>(.*?)</script>",
        Pattern.DOTALL or Pattern.CASE_INSENSITIVE
    )
    return p.matcher(this).replaceAll("")
}