package com.example.phonespecs.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.phonespecs.R
import com.example.phonespecs.databinding.ActivityMainBinding
import com.example.phonespecs.ui.details.DetailsFragment
import com.example.phonespecs.ui.phone.PhoneListingFragment
import com.example.phonespecs.utils.Constants.DETAILS_FRAGMENT
import com.example.phonespecs.utils.Constants.PHONE_LISTING_FRAGMENT
import dagger.android.support.DaggerAppCompatActivity

private const val CURRENT_FRAGMENT = "current_fragment"
private const val PHONE_SLUG = "phone_slug"

class MainContainerActivity : DaggerAppCompatActivity(), PhoneListingFragment.NavigationInterface {
    private var currentFragment: String? = null
    private var slug: String? = null
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showFragment(PhoneListingFragment.newInstance(), PHONE_LISTING_FRAGMENT)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        currentFragment = tag
        supportFragmentManager.beginTransaction().add(binding.activityMainContainer.id, fragment, tag)
            .addToBackStack(tag).commit()
    }

    override fun onPhoneItemClicked(phoneSlug: String) {
        slug = phoneSlug
        showFragment(DetailsFragment.newInstance(phoneSlug), DETAILS_FRAGMENT)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CURRENT_FRAGMENT, currentFragment)
        outState.putString(PHONE_SLUG, slug)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentFragment = savedInstanceState.getString(
            CURRENT_FRAGMENT
        )
        val slug = savedInstanceState.getString(PHONE_SLUG)
        if (currentFragment == DETAILS_FRAGMENT)
            showFragment(DetailsFragment.newInstance(slug!!), currentFragment)
        else {
            showFragment(PhoneListingFragment.newInstance(), PHONE_LISTING_FRAGMENT)
        }
    }
}