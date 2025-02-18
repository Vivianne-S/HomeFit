package com.example.homefit.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.homefit.databinding.FragmentProfileBinding
import android.util.Log

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ladda profildata
        profileViewModel.loadProfile()

        // Observera och uppdatera UI
        profileViewModel.name.observe(viewLifecycleOwner, Observer { name ->
            Log.d("ProfileFragment", "Name: $name")
            binding.textViewName.text = name ?: "YOUR NAME"
        })

        profileViewModel.age.observe(viewLifecycleOwner, Observer { age ->
            Log.d("ProfileFragment", "Age: $age")
            binding.textViewAge.text = age ?: "N/A"
        })

        profileViewModel.gender.observe(viewLifecycleOwner, Observer { gender ->
            Log.d("ProfileFragment", "Gender: $gender")
            binding.textViewGender.text = gender ?: "N/A"
        })

        profileViewModel.weight.observe(viewLifecycleOwner, Observer { weight ->
            Log.d("ProfileFragment", "Weight: $weight")
            binding.textViewWeight.text = weight ?: "N/A"
        })

        profileViewModel.goal.observe(viewLifecycleOwner, Observer { goal ->
            Log.d("ProfileFragment", "Goal: $goal")
            binding.textViewGoal.text = goal ?: "N/A"
        })

        profileViewModel.length.observe(viewLifecycleOwner, Observer { length ->
            Log.d("ProfileFragment", "Length: $length")
            binding.textViewLength.text = length ?: "N/A"
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
