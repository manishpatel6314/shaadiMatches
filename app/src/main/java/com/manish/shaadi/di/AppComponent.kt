package com.manish.shaadi.di

import android.app.Application
import com.manish.shaadi.di.module.ActivityModule
import com.manish.shaadi.di.module.AppModule
import com.manish.shaadi.helper.AppController
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (AndroidInjectionModule::class),
        (AppModule::class),
        (ActivityModule::class)
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppController)
}