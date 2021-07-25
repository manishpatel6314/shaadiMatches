package com.manish.shaadi.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manish.shaadi.di.ViewModelKey
import com.manish.shaadi.di.factory.AppModelFactory
import com.manish.shaadi.login.viewmodel.LoginViewModel
import com.manish.shaadi.shaadimatch.viewmodels.MatchesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MatchesViewModel::class)
    abstract fun bindMatchViewModel(viewModel: MatchesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppModelFactory): ViewModelProvider.Factory
}