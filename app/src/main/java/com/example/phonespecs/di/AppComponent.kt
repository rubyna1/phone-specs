package com.example.phonespecs.di

import android.app.Application
import com.example.phonespecs.PhoneSpecs
import com.example.phonespecs.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class),
        (ActivityBindingModule::class), (FragmentBindingModule::class), (ApplicationBindingModule::class), (RxModule::class),
        (ApiModule::class), (AppModule::class)]
)
interface AppComponent : AndroidInjector<PhoneSpecs> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}
