package com.hydroh.yamibo.data.parser

import com.hydroh.yamibo.model.Post
import com.hydroh.yamibo.model.Section
import com.hydroh.yamibo.model.SectionGroup
import org.jsoup.nodes.Document

fun Document.getSectionGroups(): ArrayList<SectionGroup> {
    val groups = ArrayList<SectionGroup>()
    select("div.bm.bmw div.bm_h.cl").forEach { groupElem ->
        val title = groupElem.select("h2").first()?.text() ?: ""

        val sections = ArrayList<Section>()
        val sectionElems = groupElem.nextElementSibling()
        sectionElems?.select("div.bm_c tr")?.forEach { sectionElem ->
            if (sectionElem.children().size >= 2) {
                sectionElem.child(1).apply {
                    sections.add(
                        Section(
                            title = select("h2 a").first()?.ownText() ?: "",
                            desc = select("p.xg2").first()?.ownText() ?: "",
                            url = select("h2 a").first()?.attr("href") ?: "",
                            newCount = select("h2 em").first()?.ownText() ?: "",
                            postCount = "", // TODO
                            replyCount = "",
                        )
                    )
                }
            }
        }

        if (sections.isEmpty()) return@forEach

        val group = SectionGroup(
            title = title,
            sections = sections,
            initialExpanded = true,
        )
        groups.add(group)
    }
    return groups
}

fun Document.getPosts(top: Boolean = false): ArrayList<Post> {
    val posts = ArrayList<Post>()
    select("table#threadlisttableid tbody").forEach { postElem ->
        if (postElem.id().startsWith(if (top) "stickthread" else "normalthread")) {
            postElem.apply {
                val titleElem = postElem.child(0).child(1).select("a.s.xst").first()
                val tagElem = postElem?.child(0)?.child(1)?.select("em a")?.first()
                posts.add(
                    Post(
                        title = titleElem?.ownText() ?: "",
                        author = select("td.by cite a").first()?.ownText() ?: "",
                        url = titleElem?.attr("href") ?: "",
                        tag = if (tagElem != null) "[${tagElem.ownText()}]" else "",
                        postTime = select("td.by em span").first()?.ownText() ?: "",
                        replyCount = select("td.num a").first()?.ownText() ?: "0",
                    )
                )
            }
        }
    }
    return posts
}
