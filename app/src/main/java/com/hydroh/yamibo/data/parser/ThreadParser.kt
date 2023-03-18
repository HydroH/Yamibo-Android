package com.hydroh.yamibo.data.parser

import com.hydroh.yamibo.model.Reply
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

fun Element.simplify(): Element {
    select("[style*=display:none], font[class=jammer] i").remove() // TODO: Move <i> to other places
//    getElementsByAttribute("id").removeAttr("id")
//    getElementsByAttribute("class").removeAttr("class")
    return this
}

fun Document.getReplies(): ArrayList<Reply> {
    val replies = ArrayList<Reply>()
    select("table.plhin").forEach { replyElem ->
        replyElem.apply {
            replies.add(
                Reply(
                author = select("td.pls div.auth1 a.xw1").first()?.ownText() ?: "",
                avatarUrl = select("td.pls div.avatar a.avtm img").first()?.attr("src") ?: "",
                text = select("td.plc div.pct td.t_f").first()?.simplify()?.html() ?: "",
                time = select("td.plc div.auth1 em").first()?.ownText() ?: "",
            ))
        }
    }
    return replies
}