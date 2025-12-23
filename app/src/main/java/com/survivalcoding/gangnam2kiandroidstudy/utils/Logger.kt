package com.survivalcoding.gangnam2kiandroidstudy.utils

import android.util.Log
import com.survivalcoding.gangnam2kiandroidstudy.BuildConfig


object Logger {
    private val ENABLE_LOGGING = BuildConfig.DEBUG

    fun d(tag: String, message: String) {
        if (ENABLE_LOGGING) {
            Log.d(tag, message)
        }
    }

    // 추후 필요시 추가
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (ENABLE_LOGGING) {
            Log.e(tag, message, throwable)
        }
    }
}
