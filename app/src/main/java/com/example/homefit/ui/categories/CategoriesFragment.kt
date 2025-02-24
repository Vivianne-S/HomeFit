package com.example.homefit.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentCategoriesBinding
import android.view.View.OnClickListener // Import OnClickListener
import androidx.navigation.navGraphViewModels

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val exerciseType = arguments?.getString("exerciseType")
        val layoutId = when (exerciseType) {
            "arms" -> R.layout.fragment_arms_exercises
            "back" -> R.layout.fragment_back_exercises
            // Lägg till fler fall här
            else -> R.layout.fragment_arms_exercises
            //inflater.inflate(layoutId, container, false)
        }*/
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI-element
        val cardViewArms: CardView = binding.armsCardview
        val cardViewLegs: CardView = binding.legsCardview
        val cardViewChest: CardView = binding.chestCardview
        val cardViewBack: CardView = binding.backCardview
        val cardViewCore: CardView = binding.coreCardview

        //clicklistener for Arms Category
        cardViewArms.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesFragment_to_armExercisesFragment)
        }

        //clicklistener for Legs Category
        cardViewLegs.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesFragment_to_legs_exercisesFragment)
        }

        //clicklistener for Chest Category
        cardViewChest.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesFragment_to_chest_exercisesFragment)
        }

        //clicklistener for Back Category
        cardViewBack.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesFragment_to_back_exercisesFragment)
        }

        //clicklistener for Core Category
        cardViewCore.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesFragment_to_core_exercisesFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}