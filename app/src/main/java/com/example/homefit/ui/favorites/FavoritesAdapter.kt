package com.example.homefit.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homefit.databinding.ItemFavoriteBinding
import com.example.homefit.ui.data.WorkoutData

class FavoritesAdapter(
    private val workoutDataList: MutableList<WorkoutData>,
    private val onRemoveFavorite: (WorkoutData) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val workout = workoutDataList[position]
        holder.bind(workout)
    }

    override fun getItemCount() = workoutDataList.size

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workoutData: WorkoutData) {
            binding.tvWorkoutName.text = workoutData.name
            binding.tvWorkoutDescription.text = workoutData.description
            binding.tvCalories.text = "${workoutData.caloriesBurned} kcal"
            binding.btnRemoveFavorite.setOnClickListener {
                onRemoveFavorite(workoutData)
            }
        }
    }
}