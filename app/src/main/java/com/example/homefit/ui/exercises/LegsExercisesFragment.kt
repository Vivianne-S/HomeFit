package com.example.homefit.ui.exercises

import android.app.Notification.Action
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentLegsExercisesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LegsExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LegsExercisesFragment : Fragment() {
    private var _binding: FragmentLegsExercisesBinding? = null
    private val binding get() = _binding!!
    lateinit var action : NavDirections

    companion object {
        fun newInstance() = LegsExercisesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLegsExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI-element
        val cardViewSquats: CardView = binding.squatsCardview
        val cardViewSide: CardView = binding.sidelungeCardview
        val cardViewCalf: CardView = binding.calfraisesCardview
        val cardViewGlute: CardView = binding.glutebrideCardview
        val cardViewSplit: CardView = binding.splitsquatsCardview

        cardViewSquats.setOnClickListener {
            action = LegsExercisesFragmentDirections.actionLegsExercisesFragmentToWorkoutFragment(6)
            findNavController().navigate(action)
        }
        cardViewSplit.setOnClickListener {
            action = LegsExercisesFragmentDirections.actionLegsExercisesFragmentToWorkoutFragment(7)
            findNavController().navigate(action)
        }
        cardViewGlute.setOnClickListener {
            action = LegsExercisesFragmentDirections.actionLegsExercisesFragmentToWorkoutFragment(8)
            findNavController().navigate(action)
        }
        cardViewSide.setOnClickListener {
            action = LegsExercisesFragmentDirections.actionLegsExercisesFragmentToWorkoutFragment(9)
            findNavController().navigate(action)
        }
        cardViewCalf.setOnClickListener {
            action = LegsExercisesFragmentDirections.actionLegsExercisesFragmentToWorkoutFragment(10)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}