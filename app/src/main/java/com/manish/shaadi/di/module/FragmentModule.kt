package com.manish.shaadi.di.module

import com.manish.shaadi.shaadimatch.ui.MatchesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMatchScreen(): MatchesFragment


}