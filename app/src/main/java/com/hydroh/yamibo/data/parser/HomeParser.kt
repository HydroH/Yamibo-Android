package com.hydroh.yamibo.data.parser

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
        val group = SectionGroup(
            title = title,
            sections = sections,
            initialExpanded = true,
        )
        groups.add(group)
    }
    return groups
}