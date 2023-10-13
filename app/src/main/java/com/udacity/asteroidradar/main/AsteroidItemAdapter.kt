package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidItemAdapter(
    private val dataSet: Array<Asteroid>
) : RecyclerView.Adapter<AsteroidItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class ViewHolder private constructor(
        private val binding: AsteroidItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroidItemCodename.text = asteroid.codename
            binding.asteroidItemCloseApproachDate.text = asteroid.closeApproachDate
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}