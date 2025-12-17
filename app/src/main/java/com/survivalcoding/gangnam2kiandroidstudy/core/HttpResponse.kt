package com.survivalcoding.gangnam2kiandroidstudy.core

data class HttpResponse<T>(
    val statusCode: Int,
    val headers: Map<String, List<String>>,
    val body: T? = null,
)
