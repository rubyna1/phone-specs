package com.example.phonespecs.di.modules

import com.example.phonespecs.di.scopes.ActivityScope
import com.example.phonespecs.ui.main.MainContainerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainContainerActivity
}