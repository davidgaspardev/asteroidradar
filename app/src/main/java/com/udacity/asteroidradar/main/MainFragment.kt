package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.asteroids
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val asteroids = mutableListOf<Asteroid>()
        val asteroidListAdapter = AsteroidItemAdapter(asteroids, AsteroidItemListener { asteroid ->
            viewModel.onAsteroidItemClicked(asteroid)
        })
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        binding.asteroidRecycler.adapter = asteroidListAdapter

        viewModel.asteroidList.observe(viewLifecycleOwner) {asteroidList ->
            asteroidList?.let {
                asteroids.clear()
                asteroids.addAll(asteroidList)
                asteroidListAdapter.notifyDataSetChanged()
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
        return true
    }
}
