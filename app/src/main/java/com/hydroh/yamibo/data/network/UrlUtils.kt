package com.hydroh.yamibo.data.network


object UrlUtils {
    private const val APP_UPDATE_URL = "https://yamibo-android.firebaseapp.com/version.json"

    private const val BASE_URL = "https://bbs.yamibo.com/"
    private const val DEFAULT_URL = "${BASE_URL}forum.php"

    private const val SEARCH_FORUM_URL = "${BASE_URL}search.php?mod=forum"
    private const val FAVORITE_PAGE_URL = "${BASE_URL}home.php?mod=space&do=favorite&view=me"
    private const val MESSAGE_MAIL_PAGE_URL = "${BASE_URL}home.php?mod=space&do=pm"
    private const val MESSAGE_MAIL_PRIVATE_PAGE_URL =
        "${BASE_URL}home.php?mod=space&do=pm&subop=view&touid=%s#last"
    private const val MESSAGE_REPLY_PAGE_URL = "${BASE_URL}home.php?mod=space&do=notice&view=mypost"
    private const val LOGIN_FORM_URL =
        "${BASE_URL}member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login"
    private const val LOGIN_REQUEST_URL =
        "${BASE_URL}member.php?mod=logging&action=login&loginsubmit=yes&handlekey=login&loginhash=%s&inajax=1"

    private const val AVATAR_DEFAULT_URL = "${BASE_URL}uc_server/images/noavatar_%s.gif"

    @JvmStatic
    fun getFullUrl(url: String) =
        if (url.startsWith("http")) url
        else BASE_URL + url.removePrefix("/")

    @JvmStatic
    fun getDefaultUrl() = DEFAULT_URL

    @JvmStatic
    fun isDefaultUrl(url: String) = (DEFAULT_URL == url || DEFAULT_URL == getFullUrl(url))

    @JvmStatic
    fun getAppUpdateUrl() = APP_UPDATE_URL

    @JvmStatic
    fun getSearchForumUrl() = SEARCH_FORUM_URL

    @JvmStatic
    fun getFavoritePageUrl() = FAVORITE_PAGE_URL

    @JvmStatic
    fun getMessageMailPageUrl() = MESSAGE_MAIL_PAGE_URL

    @JvmStatic
    fun getMessageMailPrivatePageUrl(uid: String) = MESSAGE_MAIL_PRIVATE_PAGE_URL.format(uid)

    @JvmStatic
    fun getMessageReplyPageUrl() = MESSAGE_REPLY_PAGE_URL

    @JvmStatic
    fun getLoginFormUrl() = LOGIN_FORM_URL

    @JvmStatic
    fun getLoginRequestUrl(loginHash: String) = LOGIN_REQUEST_URL.format(loginHash)

    @JvmStatic
    fun getAvatarDefaultUrl(size: String) = AVATAR_DEFAULT_URL.format(size)

    class AvatarSize {
        companion object {
            const val SMALL = "small"
            const val MIDDLE = "middle"
            const val BIG = "big"
        }
    }
}

fun String.getFullUrl(): String {
    return UrlUtils.getFullUrl(this)
}