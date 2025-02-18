package com.example.homefit.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.homefit.R
import com.example.homefit.databinding.FragmentProfileBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var barChart: BarChart

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hitta Toolbar och konfigurera den
        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back_to_last_fragment)

        // Hantera tillbaka-knappen i toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // UI-element
        val textViewName: TextView = binding.textViewName
        val editTextAge: EditText = binding.editTextAge
        val editTextGender: EditText = binding.editTextGender
        val editTextWeight: EditText = binding.editTextWeight
        val editTextGoal: EditText = binding.editTextGoal
        val editTextLength: EditText = binding.editTextLength
        val btnSaveProfile = binding.btnSaveProfile
        barChart = binding.WeightBarChart

        // Lottie-animation
        val exerciseAnimation = binding.exerciseAnimation

        // Ladda in existerande data från ViewModel
        viewModel.name.observe(viewLifecycleOwner) { textViewName.text = it }
        viewModel.age.observe(viewLifecycleOwner) { editTextAge.setText(it) }
        viewModel.gender.observe(viewLifecycleOwner) { editTextGender.setText(it) }
        viewModel.weight.observe(viewLifecycleOwner) { editTextWeight.setText(it) }
        viewModel.goal.observe(viewLifecycleOwner) { editTextGoal.setText(it) }
        viewModel.length.observe(viewLifecycleOwner) { editTextLength.setText(it) }

        viewModel.loadProfile() // Ladda in användarens profil
        setupBarChart() // Ställ in diagrammet

        // När användaren sparar sin profil
        btnSaveProfile.setOnClickListener {
            val name = textViewName.text.toString()
            val age = editTextAge.text.toString()
            val gender = editTextGender.text.toString()
            val weight = editTextWeight.text.toString()
            val goal = editTextGoal.text.toString()
            val length = editTextLength.text.toString()

            viewModel.updateProfile(name, age, gender, weight, goal, length)
            Toast.makeText(requireContext(), "Profil sparad!", Toast.LENGTH_SHORT).show()
        }
    }

    // Metod för att ställa in stapeldiagrammet
    private fun setupBarChart() {
        val barEntries = arrayListOf(
            BarEntry(1f, 70f),
            BarEntry(2f, 68f),
            BarEntry(3f, 66f),
            BarEntry(4f, 65f),
            BarEntry(5f, 64f)
        )

        val barDataSet = BarDataSet(barEntries, "Viktförändring")
        barDataSet.color = Color.BLUE
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 12f

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.description.isEnabled = false
        barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
