package com.example.homefit.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.homefit.databinding.FragmentProfileBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.example.homefit.ui.profile.ProfileViewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var barChart: BarChart
    private lateinit var barData: BarData
    private lateinit var barDataSet: BarDataSet
    private val barEntries = ArrayList<BarEntry>()

    companion object {
        fun newInstance() = ProfileFragment()
    }

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

        // UI-element
        val textViewName: TextView = binding.textViewName
        val editTextAge: EditText = binding.editTextAge
        val editTextGender: EditText = binding.editTextGender
        val editTextWeight: EditText = binding.editTextWeight
        val editTextGoal: EditText = binding.editTextGoal
        val editTextLength: EditText = binding.editTextLength
        val btnSaveProfile = binding.btnSaveProfile
        barChart = binding.WeightBarChart

        // Ladda in existerande data
        viewModel.name.observe(viewLifecycleOwner) { textViewName.text = it }
        viewModel.age.observe(viewLifecycleOwner) { editTextAge.setText(it) }
        viewModel.gender.observe(viewLifecycleOwner) { editTextGender.setText(it) }
        viewModel.weight.observe(viewLifecycleOwner) { editTextWeight.setText(it) }
        viewModel.goal.observe(viewLifecycleOwner) { editTextGoal.setText(it) }
        viewModel.length.observe(viewLifecycleOwner) { editTextLength.setText(it) }

        viewModel.loadProfile()
        setupBarChart()

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

    private fun setupBarChart() {
        barEntries.add(BarEntry(1f, 2f))
        barDataSet = BarDataSet(barEntries, "Viktförändring")
        barDataSet.color = Color.RED
        barData = BarData(barDataSet)
        barChart.data = barData
        barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}