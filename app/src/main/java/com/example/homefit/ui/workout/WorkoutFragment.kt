package com.example.homefit.ui.workout

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homefit.R
import com.example.homefit.databinding.FragmentWorkoutBinding
import com.example.homefit.ui.data.WorkoutData
import com.example.homefit.ui.profile.ProfileViewModel
import com.example.homefit.ui.workout.WorkoutViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private var metValue: Double = 0.0

    // ViewModel-instans
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var profileViewModel: ProfileViewModel

    val args : WorkoutFragmentArgs by navArgs()

    companion object {
        fun newInstance() = WorkoutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        val imgView = binding.imageViewWorkout
        val wrkDescription = binding.workoutDescription
        val wrkName = binding.workoutName
        val workoutNr = args.Workout

        if (workoutNr == 1) {
            imgView.setImageResource(R.drawable.dips2)
            wrkName.text = getString(R.string.exercise_chair_dips)
            metValue = 5.0
            wrkDescription.text = getString(R.string.description_chair_dips)
        } else if (workoutNr == 2) {
            imgView.setImageResource(R.drawable.armcirlces1)
            wrkName.text = getString(R.string.exercise_arm_circles)
            metValue = 3.5
            wrkDescription.text = getString(R.string.description_arm_circles)
        } else if (workoutNr == 3) {
            imgView.setImageResource(R.drawable.chatauranga2)
            wrkName.text = getString(R.string.exercise_chautaranga_pushups)
            metValue = 4.0
            wrkDescription.text = getString(R.string.description_chaturanga)
        } else if (workoutNr == 4) {
            imgView.setImageResource(R.drawable.wallangel2)
            wrkName.text = getString(R.string.exercise_wall_angels)
            metValue = 2.5
            wrkDescription.text = getString(R.string.description_wall_angels)
        } else if (workoutNr == 5) {
            imgView.setImageResource(R.drawable.armlateralraises2)
            wrkName.text = getString(R.string.exercise_arm_lateral_raises)
            metValue = 3.5
            wrkDescription.text = getString(R.string.description_arm_lateral_raises)
        } else if (workoutNr == 6) {
            imgView.setImageResource(R.drawable.squat)
            wrkName.text = getString(R.string.exercise_squats)
            metValue = 5.0
            wrkDescription.text = getString(R.string.description_squats)
        } else if (workoutNr == 7) {
            imgView.setImageResource(R.drawable.splitsquats2)
            wrkName.text = getString(R.string.exercise_split_squats)
            metValue = 4.5
            wrkDescription.text = getString(R.string.description_split_squats)
        } else if (workoutNr == 8) {
            imgView.setImageResource(R.drawable.glutebridge2)
            wrkName.text = getString(R.string.exercise_glute_bridge)
            metValue = 4.0
            wrkDescription.text = getString(R.string.description_glute_bridge)
        } else if (workoutNr == 9) {
            imgView.setImageResource(R.drawable.sidelunge)
            wrkName.text = getString(R.string.exercise_side_lunge)
            metValue = 4.5
            wrkDescription.text = getString(R.string.description_side_lunge)
        } else if (workoutNr == 10) {
            imgView.setImageResource(R.drawable.calfraises2)
            wrkName.text = getString(R.string.exercise_calf_raises)
            metValue = 3.5
            wrkDescription.text = getString(R.string.description_calf_raises)
        } else if (workoutNr == 11) {
            imgView.setImageResource(R.drawable.pushups2)
            wrkName.text = getString(R.string.exercise_pushups)
            metValue = 4.0
            wrkDescription.text = getString(R.string.description_push_ups)
        } else if (workoutNr == 12) {
            imgView.setImageResource(R.drawable.widepushups2)
            wrkName.text = getString(R.string.exercise_wide_pushups)
            metValue = 4.2
            wrkDescription.text = getString(R.string.description_wide_push_ups)
        } else if (workoutNr == 13) {
            imgView.setImageResource(R.drawable.burpees2)
            wrkName.text = getString(R.string.exercise_burpees)
            metValue = 8.0
            wrkDescription.text = getString(R.string.description_burpees)
        } else if (workoutNr == 14) {
            imgView.setImageResource(R.drawable.inclinepushup2)
            wrkName.text = getString(R.string.exercise_incline_pushups)
            metValue = 3.8
            wrkDescription.text = getString(R.string.description_incline_push_ups)
        } else if (workoutNr == 15) {
            imgView.setImageResource(R.drawable.declinepushup2)
            wrkName.text = getString(R.string.exercise_decline_pushups)
            metValue = 4.2
            wrkDescription.text = getString(R.string.description_decline_push_ups)
        } else if (workoutNr == 16) {
            imgView.setImageResource(R.drawable.superman2)
            wrkName.text = getString(R.string.exercise_superman)
            metValue = 3.0
            wrkDescription.text = getString(R.string.description_superman)
        } else if (workoutNr == 17) {
            imgView.setImageResource(R.drawable.goodmorning2)
            wrkName.text = getString(R.string.exercise_good_morning)
            metValue = 3.5
            wrkDescription.text = getString(R.string.description_good_morning)
        } else if (workoutNr == 18) {
            imgView.setImageResource(R.drawable.reverseplank)
            wrkName.text = getString(R.string.exercise_reverse_plank)
            metValue = 3.0
            wrkDescription.text = getString(R.string.description_reverse_plank)
        } else if (workoutNr == 19) {
            imgView.setImageResource(R.drawable.cat)
            wrkName.text = getString(R.string.exercise_cat_cow)
            metValue = 2.0
            wrkDescription.text = getString(R.string.description_cat_cow)
        } else if (workoutNr == 20) {
            imgView.setImageResource(R.drawable.reverseflys2)
            wrkName.text = getString(R.string.exercise_reverse_flys)
            metValue = 4.0
            wrkDescription.text = getString(R.string.description_reverse_flys)
        } else if (workoutNr == 21) {
            imgView.setImageResource(R.drawable.plank)
            wrkName.text = getString(R.string.exercise_plank)
            metValue = 3.3
            wrkDescription.text = getString(R.string.description_plank)
        } else if (workoutNr == 22) {
            imgView.setImageResource(R.drawable.crunches2)
            wrkName.text = getString(R.string.exercise_crunches)
            metValue = 3.8
            wrkDescription.text = getString(R.string.description_crunches)
        } else if (workoutNr == 23) {
            imgView.setImageResource(R.drawable.bcrunches2)
            wrkName.text = getString(R.string.exercise_bicycle_crunches)
            metValue = 4.0
            wrkDescription.text = getString(R.string.description_bicycle_crunches)
        } else if (workoutNr == 24) {
            imgView.setImageResource(R.drawable.legraises2)
            wrkName.text = getString(R.string.exercise_leg_raises)
            metValue = 3.5
            wrkDescription.text = getString(R.string.description_leg_raises)
        } else if (workoutNr == 25) {
            imgView.setImageResource(R.drawable.heeltap1)
            wrkName.text = getString(R.string.exercise_heel_taps)
            metValue = 3.0
            wrkDescription.text = getString(R.string.description_heel_taps)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiera ViewModel
        workoutViewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        // Ladda användardata från Firestore
        profileViewModel.loadProfile()

        // Observera favoriteStatus LiveData för att visa Toast
        workoutViewModel.favoriteStatus.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
            }
        }

        // Hämta träningsnamn och beskrivning från användargränssnittet
        val workoutName = binding.workoutName.text.toString()
        val workoutDescription = binding.workoutDescription.text.toString()

        // Kontrollera om övningen redan är sparad som favorit
        checkIfFavorite(workoutName)

        binding.imageButtonFavorite.setOnClickListener {
            val isFavorite = binding.imageButtonFavorite.tag == "favorite"

            if (isFavorite) {
                // Ta bort favoriten
                removeFavorite(workoutName)
                binding.imageButtonFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.imageButtonFavorite.tag = "not_favorite"
            } else {
                // Spara favoriten
                val workoutImageResId = when (args.Workout) {
                    1 -> R.drawable.dips2
                    2 -> R.drawable.armcirlces1
                    3 -> R.drawable.chatauranga2
                    4 -> R.drawable.wallangel2
                    5 -> R.drawable.armlateralraises2
                    6 -> R.drawable.squat
                    7 -> R.drawable.splitsquats2
                    8 -> R.drawable.glutebridge2
                    9 -> R.drawable.sidelunge
                    10 -> R.drawable.calfraises2
                    11 -> R.drawable.pushups2
                    12 -> R.drawable.widepushups2
                    13 -> R.drawable.burpees2
                    14 -> R.drawable.inclinepushup2
                    15 -> R.drawable.declinepushup2
                    16 -> R.drawable.superman2
                    17 -> R.drawable.goodmorning2
                    18 -> R.drawable.reverseplank
                    19 -> R.drawable.cat
                    20 -> R.drawable.reverseflys2
                    21 -> R.drawable.plank
                    22 -> R.drawable.crunches2
                    23 -> R.drawable.bcrunches2
                    24 -> R.drawable.legraises2
                    25 -> R.drawable.heeltap1
                    else -> R.drawable.default_workout_image // Fallback om inget matchar
                }

                workoutViewModel.saveFavorite(WorkoutData(workoutName, workoutDescription, 0, workoutImageResId, metValue))
                binding.imageButtonFavorite.setImageResource(R.drawable.baseline_favorite_24)
                binding.imageButtonFavorite.tag = "favorite"
            }
        }

        // När användaren startar träningspasset
        binding.startTimerBtn.setOnClickListener {
            workoutViewModel.startWorkout()
            Toast.makeText(requireContext(), R.string.training_started, Toast.LENGTH_SHORT).show()
        }

        // När användaren avslutar träningspasset
        binding.stopTimerBtn.setOnClickListener {
            profileViewModel.weight.observe(viewLifecycleOwner) { weightStr ->
                val userWeight = weightStr.toDoubleOrNull() ?: 75.0
                val summary = workoutViewModel.endWorkout(userWeight, metValue)
                showWorkoutSummary(summary)
            }
        }
    }

    private fun checkIfFavorite(workoutName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Log.e("WorkoutFragment", "User not logged in.")
            return
        }

        val db = FirebaseFirestore.getInstance()

        // Kontrollera om övningen finns i användarens favoriter
        db.collection("users").document(userId)
            .collection("favorites")
            .document(workoutName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Om övningen finns i favoriter, sätt hjärtat till rött
                    binding.imageButtonFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    binding.imageButtonFavorite.tag = "favorite"
                } else {
                    // Annars, sätt hjärtat till ofyllt
                    binding.imageButtonFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    binding.imageButtonFavorite.tag = "not_favorite"
                }
            }
            .addOnFailureListener { e ->
                Log.e("WorkoutFragment", "Error checking favorite status", e)
            }
    }

    private fun removeFavorite(workoutName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Log.e("WorkoutFragment", "User not logged in.")
            return
        }

        val db = FirebaseFirestore.getInstance()

        // Ta bort favoriten från Firestore
        db.collection("users").document(userId)
            .collection("favorites")
            .document(workoutName)
            .delete()
            .addOnSuccessListener {
                Log.d("WorkoutFragment", "Removed favorite: $workoutName")
                Toast.makeText(requireContext(), R.string.removed_from_favorites, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("WorkoutFragment", "Error removing favorite", e)
                Toast.makeText(requireContext(), getString(R.string.failed_to_remove_favorite, e.message), Toast.LENGTH_SHORT).show()
            }
    }

    // Visa sammanfattning av träningen
    private fun showWorkoutSummary(summary: WorkoutSummary) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.training_summary)
            .setMessage("${getString(R.string.time)}: ${summary.duration} min\n${getString(R.string.calories)}: ${summary.calories.toInt()} kcal")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}