package com.example.homefit.ui.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentCoreExercisesBinding
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

        //clicklistener for Arms Category
        cardViewSquats.setOnClickListener {
            findNavController().navigate(R.id.action_legs_exercisesFragment_to_Workout_Fragment)
        }

        //clicklistener for Legs Category
        cardViewSide.setOnClickListener {
            findNavController().navigate(R.id.action_legs_exercisesFragment_to_Workout_Fragment)
        }

        //clicklistener for Chest Category
        cardViewCalf.setOnClickListener {
            findNavController().navigate(R.id.action_legs_exercisesFragment_to_Workout_Fragment)
        }

        //clicklistener for Back Category
        cardViewGlute.setOnClickListener {
            findNavController().navigate(R.id.action_legs_exercisesFragment_to_Workout_Fragment)
        }

        //clicklistener for Core Category
        cardViewSplit.setOnClickListener {
            findNavController().navigate(R.id.action_legs_exercisesFragment_to_Workout_Fragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}