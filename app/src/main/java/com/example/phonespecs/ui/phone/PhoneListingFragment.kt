package com.example.phonespecs.ui.phone

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonespecs.R
import com.example.phonespecs.databinding.FragmentPhoneListingBinding
import com.example.phonespecs.entity.Phones
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PhoneListingFragment : DaggerFragment(), ItemPhoneDataAdapter.OnItemCallbacks,
    ItemSearchAdapter.OnItemCallbacks {
    @Inject
    lateinit var phoneDataViewModel: PhoneDataViewModel
    lateinit var binding: FragmentPhoneListingBinding
    private lateinit var adapter: ItemPhoneDataAdapter
    private lateinit var searchAdapter: ItemSearchAdapter
    private lateinit var listener: NavigationInterface
    private val listOfData = mutableListOf<Phones>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPhoneListingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        adapter = ItemPhoneDataAdapter(requireContext(), this)
        binding.fragmentPhoneListingRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.fragmentPhoneListingRecyclerView.adapter = adapter
        searchAdapter = ItemSearchAdapter(requireContext(), listOfData, this)
        binding.fragmentPhoneListingSearchRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.fragmentPhoneListingSearchRecyclerView.adapter = searchAdapter
        binding.fragmentPhoneListingSearchRecyclerView.setItemViewCacheSize(listOfData.size)
        phoneDataViewModel.fetchAllPhonesDataFromDb()
        phoneDataViewModel.dataFromDb?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.fragmentPhoneListingProgressBar.visibility = View.GONE
                binding.fragmentPhoneListingRecyclerView.visibility = View.VISIBLE
                binding.fragmentPhoneListingSearchRecyclerView.visibility = View.GONE
                binding.fragmentPhoneListingErrorView.visibility = View.GONE
            }
        })
        binding.fragmentPhoneListingSearchEditText.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard()
                    phoneDataViewModel.search(binding.fragmentPhoneListingSearchEditText.text.toString())
                    binding.fragmentPhoneListingProgressBar.visibility = View.VISIBLE
                    binding.fragmentPhoneListingBackImageView.visibility = View.VISIBLE
                    binding.fragmentPhoneListingSearchRecyclerView.visibility = View.GONE
                    binding.fragmentPhoneListingErrorView.visibility = View.GONE
                    binding.fragmentPhoneListingRecyclerView.visibility = View.GONE
                    return true
                }
                return false
            }
        })
        binding.fragmentPhoneListingBackImageView.setOnClickListener {
            binding.fragmentPhoneListingSearchEditText.text.clear()
            binding.fragmentPhoneListingSearchEditText.clearFocus()
            binding.fragmentPhoneListingProgressBar.visibility = View.GONE
            binding.fragmentPhoneListingRecyclerView.visibility = View.VISIBLE
            binding.fragmentPhoneListingBackImageView.visibility = View.GONE
            binding.fragmentPhoneListingSearchRecyclerView.visibility = View.GONE
            binding.fragmentPhoneListingErrorView.visibility = View.GONE
        }
        phoneDataViewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            if (it.data != null && it.data!!.phones.isNotEmpty()) {
                listOfData.clear()
                listOfData.addAll(it.data!!.phones)
                searchAdapter.notifyDataSetChanged()
                binding.fragmentPhoneListingProgressBar.visibility = View.GONE
                binding.fragmentPhoneListingRecyclerView.visibility = View.GONE
                binding.fragmentPhoneListingErrorView.visibility = View.GONE
                binding.fragmentPhoneListingBackImageView.visibility = View.VISIBLE
                binding.fragmentPhoneListingSearchRecyclerView.visibility = View.VISIBLE
            } else {
                binding.fragmentPhoneListingBackImageView.visibility = View.VISIBLE
                binding.fragmentPhoneListingProgressBar.visibility = View.GONE
                binding.fragmentPhoneListingRecyclerView.visibility = View.GONE
                binding.fragmentPhoneListingErrorView.visibility = View.VISIBLE
                binding.fragmentPhoneListingErrorView.text = getString(R.string.noSuchItem)
                binding.fragmentPhoneListingSearchRecyclerView.visibility = View.GONE
            }
        })
    }

    fun hideSoftKeyboard() {
        try {
            val inputMethodManager =
                activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): PhoneListingFragment = PhoneListingFragment()
    }

    override fun onItemClicked(phoneSlug: String) {
        binding.fragmentPhoneListingSearchEditText.clearFocus()
        listener.onPhoneItemClicked(phoneSlug)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as NavigationInterface
    }

    interface NavigationInterface {
        fun onPhoneItemClicked(phoneSlug: String)
    }
}