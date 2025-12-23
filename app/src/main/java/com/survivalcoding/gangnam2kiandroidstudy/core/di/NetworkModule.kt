package com.survivalcoding.gangnam2kiandroidstudy.core.di

import com.survivalcoding.gangnam2kiandroidstudy.utils.NetworkChecker
import com.survivalcoding.gangnam2kiandroidstudy.utils.NetworkCheckerImpl
import org.koin.dsl.module

var networkModule = module {
    single<NetworkChecker> {
        NetworkCheckerImpl(get())
    }
}
