package com.survivalcoding.gangnam2kiandroidstudy.core

data class HttpResponse<T>(
    val statesCode: Int,
    val headers: Map<String, List<String>>,
    val body: T? = null,
)
