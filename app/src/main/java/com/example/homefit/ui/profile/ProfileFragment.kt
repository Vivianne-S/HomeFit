package com.example.homefit.ui.profile

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homefit.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ProfileFragment : Fragment() {

    lateinit var barChart : BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntries: ArrayList<BarEntry>

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()

        barChart.findViewById<BarChart>(R.id.WeightBarChart)

        barDataSet = BarDataSet(barEntries, "Data set")
        barData = BarData(barDataSet)

        barChart.data = barData

        barDataSet.setColors(Color.RED)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun getData(){
        barEntries.add(BarEntry(1f,2f))
    }
}