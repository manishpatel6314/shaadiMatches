package com.manish.shaadi.di.module

import com.manish.shaadi.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {


    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract  fun contributeMainActivity(): MainActivity
}