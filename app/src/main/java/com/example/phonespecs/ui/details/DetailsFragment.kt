package com.example.phonespecs.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.phonespecs.R
import com.example.phonespecs.databinding.FragmentDetailsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val SLUG = "slug"

class DetailsFragment : DaggerFragment() {
    @Inject
    lateinit var detailsPageViewModel: DetailsPageViewModel
    lateinit var binding: FragmentDetailsBinding
    private var slug: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            slug = it.getString(SLUG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
//        val details = id?.let { detailsPageViewModel.getPhoneDetailsById(it) }
//        details?.observe(viewLifecycleOwner, Observer {
//            Log.i("DetailsFragment", "$it")
//        })
        binding.fragmentDetailPageBackImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        slug?.let { detailsPageViewModel.getPhoneDetailsBySlug(it) }
        detailsPageViewModel.detailsResponse.observe(viewLifecycleOwner, Observer {
            if (it.data != null) {
                binding.fragmentDetailsMainView.visibility = View.VISIBLE
                binding.fragmentDetailPageProgressBar.visibility = View.GONE
                binding.fragmentDetailPageErrorView.visibility = View.GONE
                Glide.with(requireContext()).load(it.data!!.thumbnail)
                    .into(binding.fragmentDetailPageImageView)
                binding.fragmentDetailPageNameTextView.text = slug
                binding.fragmentDetailPageReleaseDateTextView.text = it.data?.releaseDate
                binding.fragmentDetailPageDimensionTextView.text = it.data?.dimension
                binding.fragmentDetailPageOsTextView.text = it.data?.os
                binding.fragmentDetailPageStorageTextView.text = it.data?.storage
            } else {
                binding.fragmentDetailsMainView.visibility = View.GONE
                binding.fragmentDetailPageProgressBar.visibility = View.GONE
                binding.fragmentDetailPageErrorView.visibility = View.VISIBLE
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(SLUG, param1)
                }
            }
    }
}