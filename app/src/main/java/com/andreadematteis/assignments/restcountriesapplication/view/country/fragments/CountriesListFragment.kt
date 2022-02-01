package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentListCountriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesListFragment: Fragment() {

    private lateinit var binding: FragmentListCountriesBinding
    private val viewModel: CountriesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentListCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val position = parent.getChildAdapterPosition(view)

                if (position == 0) {
                    outRect.top = 100
                }

                if (position != parent.adapter!!.itemCount - 1) {
                    outRect.bottom = 20
                }

                outRect.left = 40
                outRect.right = 40
            }
        })

        viewModel.countryList.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = CountryListAdapter(it)

            viewModel.startWatchingImageCache()
        }

        viewModel.idImage.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as CountryListAdapter).setImage(it)
        }

        viewModel.getCountries()
    }
}