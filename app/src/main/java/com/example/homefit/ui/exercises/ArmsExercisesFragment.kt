package com.example.homefit.ui.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentArmsExercisesBinding
import com.example.homefit.databinding.FragmentCategoriesBinding
import com.example.homefit.ui.categories.CategoriesFragment

class ArmsExercisesFragment : Fragment() {

    private var _binding: FragmentArmsExercisesBinding? = null
    private val binding get() = _binding!!
    lateinit var action : NavDirections

    companion object {
        fun newInstance() = ArmsExercisesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArmsExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI-element
        val cardViewDips: CardView = binding.chairdipsCardview
        val cardViewCircle: CardView = binding.armcircleCardview
        val cardViewChat: CardView = binding.chataurungaCardview
        val cardViewWallAngels: CardView = binding.wallangelsCardview
        val cardViewLatRaises: CardView = binding.armlateralraisesCardview

        //clicklistener for Arms Category
        cardViewDips.setOnClickListener {
            action = ArmsExercisesFragmentDirections.actionArmExercisesFragmentToWorkoutFragment(1)
            findNavController().navigate(action)
        }

        //clicklistener for Legs Category
        cardViewCircle.setOnClickListener {
            action = ArmsExercisesFragmentDirections.actionArmExercisesFragmentToWorkoutFragment(2)
            findNavController().navigate(action)
        }

        //clicklistener for Chest Category
        cardViewChat.setOnClickListener {
            action = ArmsExercisesFragmentDirections.actionArmExercisesFragmentToWorkoutFragment(3)
            findNavController().navigate(action)
        }

        //clicklistener for Back Category
        cardViewWallAngels.setOnClickListener {
            action = ArmsExercisesFragmentDirections.actionArmExercisesFragmentToWorkoutFragment(4)
            findNavController().navigate(action)
        }

        //clicklistener for Core Category
        cardViewLatRaises.setOnClickListener {
            action = ArmsExercisesFragmentDirections.actionArmExercisesFragmentToWorkoutFragment(5)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}