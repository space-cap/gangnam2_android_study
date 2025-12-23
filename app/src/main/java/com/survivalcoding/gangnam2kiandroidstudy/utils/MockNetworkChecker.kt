package com.survivalcoding.gangnam2kiandroidstudy.utils


class MockNetworkChecker(
    private val connected: Boolean = true
) : NetworkChecker {

    override fun isNetworkAvailable(): Boolean = connected
    override fun isInternetAvailable(): Boolean = connected
}
