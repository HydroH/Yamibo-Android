package com.hydroh.yamibo.util

import java.util.regex.Pattern

fun String.removeScripts(): String {
    val p = Pattern.compile(
        "<script[^>]*>(.*?)</script>",
        Pattern.DOTALL or Pattern.CASE_INSENSITIVE
    )
    return p.matcher(this).replaceAll("")
}

// Thank you ChatGPT!
fun String.convertBBCodeToMarkdown(): String {
    val bbCodePattern = Regex("\\[(/)?(b|i|u|s|url(=.+?)?|img|quote|code|size|color|table|tr|th|td|backcolor|list|\\*|p|align|float)]")
    var orderedListItemIndex = 0

    return bbCodePattern.replace(this) {
        val tag = it.groupValues[1]
        val tagName = it.groupValues[2]
        val tagParam = it.groupValues[3].substring(1)

        when (tagName) {
            // bold
            "b" -> if (tag == "/") "**" else "**"
            // italic
            "i" -> if (tag == "/") "_" else "_"
            //underline
            "u" -> if (tag == "/") "__" else "__"
            // strikethrough
            "s" -> if (tag == "/") "~~" else "~~"
            // hyperlink
            "url" -> if (tag == "/") ")" else "[$tagParam]("
            // image
            "img" -> if (tag == "/") ")" else "!["
            // blockquote
            "quote" -> if (tag == "/") "" else "> "
            // inline code
            "code" -> if (tag == "/") "`" else "`"
            // font size
            "size" -> if (tag == "/") "</span>" else "<span style=\"font-size: ${tagParam}pt;\">"
            // font color
            "color" -> if (tag == "/") "</span>" else "<span style=\"color: ${tagParam};\">"
            // table
            "table" -> if (tag == "/") "" else "\n"
            // table row
            "tr" -> if (tag == "/") "|--" else "| "
            // table header
            "th" -> if (tag == "/") "" else ":-:| "
            // table cell
            "td" -> if (tag == "/") "" else "| "
            // background color
            "backcolor" -> if (tag == "/") "</span>" else "<span style=\"background-color: ${tagParam};\">"
            // list
            "list" -> if (tag == "/") { orderedListItemIndex = 0; "" }
            // ordered list
            else if (tagParam == "1") { orderedListItemIndex = 1; "\n" }
            // unordered list
            else { orderedListItemIndex = 0; "\n" }
            // list item
            "[*]" -> if (tag == "/") "" else { if (orderedListItemIndex > 0) "${orderedListItemIndex++}. " else "* " }
            // paragraph
            "p" -> if (tag == "/") "\n" else ""
            // alignment
            "align" -> if (tag == "/") "</div>" else "<div style=\"text-align: ${tagParam};\">"
            // float
            "float" -> if (tag == "/") "</div>" else "<div style=\"float: ${tagParam};\">"
            // unknown tag, return empty string
            else -> ""
        }
    }
}