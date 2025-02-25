package com.example.homefit.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homefit.databinding.ItemFavoriteBinding
import com.example.homefit.ui.data.WorkoutData
import com.example.homefit.R

class FavoritesAdapter(
    private val workoutDataList: MutableList<WorkoutData>, // Lista med favoritövningar
    private val onRemoveFavorite: (WorkoutData) -> Unit // Callback för att ta bort en favorit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    // Skapar en ny ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    // Binder data till ViewHolder
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val workout = workoutDataList[position]
        holder.bind(workout)
    }

    // Returnerar antalet övningar i listan
    override fun getItemCount() = workoutDataList.size

    // ViewHolder-klass för varje övning i listan
    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

        // Binder övningsdata till UI-element
        fun bind(workoutData: WorkoutData) {
            // Sätt övningens namn
            binding.tvWorkoutName.text = workoutData.name

            // Sätt övningens beskrivning
            binding.tvWorkoutDescription.text = workoutData.description

            // Sätt antal förbrända kalorier
            binding.tvCalories.text = "${workoutData.caloriesBurned} kcal"

            // Visa bilden med hjälp av resurs-ID
            binding.imageViewWorkout.setImageResource(workoutData.imageResId)

            // Hantera klick på "ta bort favorit"-knappen
            binding.btnRemoveFavorite.setOnClickListener {
                onRemoveFavorite(workoutData)
            }
        }
    }

    // Metod för att uppdatera listan med nya data
    fun updateData(newData: List<WorkoutData>) {
        workoutDataList.clear()
        workoutDataList.addAll(newData)
        notifyDataSetChanged() // Meddela adapter om att data har ändrats
    }
}