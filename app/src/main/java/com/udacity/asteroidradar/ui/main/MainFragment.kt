package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.AsteroidFilter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val LOG_TAG = "MainFragment"

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val asteroidListAdapter = AsteroidItemAdapter(AsteroidItemListener { asteroid ->
            viewModel.onAsteroidItemClicked(asteroid)
        })
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        binding.asteroidRecycler.adapter = asteroidListAdapter

        viewModel.asteroidList.observe(viewLifecycleOwner) {asteroidList ->
            asteroidList?.let {
                asteroidListAdapter.submitList(asteroidList)
            }
        }

        viewModel.pictureOfDay.observe(viewLifecycleOwner) {pictureOfDay ->
            pictureOfDay?.let {
                val imageUrl = if (pictureOfDay.mediaType == "video") pictureOfDay.thumbnailUrl else pictureOfDay.url
                Picasso.get().load(imageUrl).into(binding.activityMainImageOfTheDay)
            }
        }

        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner) {asteroid ->
            asteroid?.let {
                val action = MainFragmentDirections.actionShowDetail(asteroid)
                findNavController().navigate(action)
                viewModel.onAsteroidDetailNavigated()
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(LOG_TAG, "onOptionsItemSelected: ${item.itemId}")
        when (item.itemId) {
            R.id.show_all_menu -> viewModel.updateFilter(AsteroidFilter.SHOW_WEEK)
            R.id.show_rent_menu -> viewModel.updateFilter(AsteroidFilter.SHOW_TODAY)
            R.id.show_buy_menu -> viewModel.updateFilter(AsteroidFilter.SHOW_SAVED)
        }
        return true
    }
}
