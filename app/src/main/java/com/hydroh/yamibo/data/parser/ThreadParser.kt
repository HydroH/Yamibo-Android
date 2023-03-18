package com.hydroh.yamibo.data.parser

import com.hydroh.yamibo.model.Reply
import com.hydroh.yamibo.model.Thread
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

fun Element.simplify(): Element {
    select("[style*=display:none], font[class=jammer] i").remove() // TODO: Move <i> to other places
//    getElementsByAttribute("id").removeAttr("id")
//    getElementsByAttribute("class").removeAttr("class")
    return this
}

fun Document.getThread(): Thread {
    val thread = Thread()
    // TODO: Add other properties
    select("table.plhin").forEach { replyElem ->
        replyElem.apply {
            thread.replies.add(
                Reply(
                author = select("td.pls div.auth1 a.xw1").first()?.ownText() ?: "",
                avatarUrl = select("td.pls div.avatar a.avtm img").first()?.attr("src") ?: "",
                text = select("td.plc div.pct td.t_f").first()?.simplify()?.html() ?: "",
                time = select("td.plc div.auth1 em").first()?.ownText() ?: "",
            ))
        }
    }
    return thread
}