package com.hydroh.yamibo.util

import android.content.Context
import com.hydroh.yamibo.R

object Consts {
    lateinit var ERROR_NETWORK: String
    lateinit var ERROR_UNKNOWN: String
    fun init(context: Context) {
        ERROR_NETWORK = context.getString(R.string.error_network)
        ERROR_UNKNOWN = context.getString(R.string.error_unknown)
    }
}