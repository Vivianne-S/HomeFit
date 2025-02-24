package com.example.homefit.ui.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homefit.R
import com.example.homefit.databinding.FragmentWorkoutBinding
import com.example.homefit.ui.data.WorkoutData

class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    // ViewModel-instans
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiera ViewModel
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        // Observera favoriteStatus LiveData för att uppdatera UI
        workoutViewModel.favoriteStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }

        // Hämta träningsnamn och beskrivning från användargränssnittet
        val workoutName = binding.workoutName.text.toString()
        val workoutDescription = binding.workoutDescription.text.toString()

        // Sätt upp en click listener för favorit-knappen
        binding.imageButtonFavorite.setOnClickListener {
            // Spara övningen som favorit (utan bild)
            workoutViewModel.saveFavorite(WorkoutData(workoutName, workoutDescription, 0, ""))

            // Ändra hjärtikonen till röd när knappen trycks
            binding.imageButtonFavorite.setImageResource(R.drawable.baseline_favorite_24)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
