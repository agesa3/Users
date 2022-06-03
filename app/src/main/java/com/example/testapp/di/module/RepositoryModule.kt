package com.example.testapp.di.module

import com.example.testapp.data.repository.MainRepository
import org.koin.dsl.module


val repoModule = module {
    single {
        MainRepository(get())
    }
}