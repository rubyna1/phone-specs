package com.example.phonespecs.di.modules

import com.example.phonespecs.di.scopes.FragmentScope
import com.example.phonespecs.ui.details.DetailsFragment
import com.example.phonespecs.ui.phone.PhoneListingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindPhoneDataFragment(): PhoneListingFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindDetailsFragment(): DetailsFragment
}