package com.example.homefit.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

        // UI-element
        val textViewName: TextView = binding.textViewName
        val editTextAge: EditText = binding.editTextAge
        val editTextGender: EditText = binding.editTextGender
        val editTextWeight: EditText = binding.editTextWeight
        val editTextGoal: EditText = binding.editTextGoal
        val editTextLength: EditText = binding.editTextLength
        val btnSaveProfile = binding.btnSaveProfile
        barChart = binding.WeightBarChart

        // Ladda in existerande data från ViewModel och uppdatera diagram när vikten ändras
        viewModel.name.observe(viewLifecycleOwner) { textViewName.text = it }
        viewModel.age.observe(viewLifecycleOwner) { editTextAge.setText(it) }
        viewModel.gender.observe(viewLifecycleOwner) { editTextGender.setText(it) }
        viewModel.weight.observe(viewLifecycleOwner) {
            editTextWeight.setText(it)
            updateBarChart(it)
        }
        // Uppdatera diagrammet när målet ändras med aktuell vikt
        viewModel.goal.observe(viewLifecycleOwner) { goal ->
            editTextGoal.setText(goal)
            updateBarChart(viewModel.weight.value ?: "0")
        }
        viewModel.length.observe(viewLifecycleOwner) { editTextLength.setText(it) }

        viewModel.loadProfile()
        setupBarChart()

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

    // Hämta användarens vikt och mål från ViewModel
    private fun setupBarChart() {
        val weightValue = viewModel.weight.value?.toFloatOrNull() ?: 0f
        val goalValue = viewModel.goal.value?.toFloatOrNull() ?: 0f

        // Skapa två uppsättningar av BarEntry för att representera både vikt och mål
        val barEntries = arrayListOf(
            BarEntry(1f, weightValue),
            BarEntry(2f, goalValue)
        )

        // Grön färg för vikten och rosa för målet
        val weightBarDataSet = BarDataSet(barEntries, "Weight vs Goal")
        weightBarDataSet.colors = listOf(Color.GREEN, Color.parseColor("#FF69B4"))
        weightBarDataSet.valueTextColor = Color.BLACK
        weightBarDataSet.valueTextSize = 12f

        val barData = BarData(weightBarDataSet)
        barChart.data = barData
        barChart.description.isEnabled = false
        barChart.invalidate()
    }

    private fun updateBarChart(weight: String) {
        val weightValue = weight.toFloatOrNull() ?: return
        val goalValue = viewModel.goal.value?.toFloatOrNull() ?: 0f  // Om goal är null sätt till 0

        // Beräkna hur många kg som är kvar till målet
        val remainingWeight = weightValue - goalValue
        val remainingText = "You have ${remainingWeight.toInt()} kg remaining to your Goal!"

        // Skapa uppsättningar för både vikt och mål
        val barEntries = arrayListOf(
            BarEntry(1f, weightValue),
            BarEntry(2f, goalValue)
        )

        // Grön för vikt och rosa för mål
        val weightBarDataSet = BarDataSet(barEntries, "Weight vs Goal")
        weightBarDataSet.colors = listOf(Color.parseColor("#0F80E2"), Color.parseColor("#FF69B4"))
        weightBarDataSet.valueTextColor = Color.BLACK
        weightBarDataSet.valueTextSize = 12f

        // Uppdatera diagrammet
        val barData = BarData(weightBarDataSet)
        barChart.data = barData
        barChart.description.isEnabled = false
        barChart.invalidate()

        // Visa texten för kvarvarande vikt
        val textViewRemainingWeight: TextView = binding.textViewRemainingWeight
        textViewRemainingWeight.text = remainingText
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
