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
import com.example.homefit.databinding.FragmentBackExercisesBinding
import com.example.homefit.databinding.FragmentChestExercisesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChestExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChestExercisesFragment : Fragment() {
    //private var param1: String? = null
    //private var param2: String? = null

    private var _binding: FragmentChestExercisesBinding? = null
    private val binding get() = _binding!!
    lateinit var action : NavDirections

    companion object {
        fun newInstance() = ChestExercisesFragment()
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
        _binding = FragmentChestExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI-element
        val cardViewPushUps: CardView = binding.pushupsCardview
        val cardViewWide: CardView = binding.widepushupsCardview
        val cardViewBurpees: CardView = binding.burpeesCardview
        val cardViewIncline: CardView = binding.inclinepushupCardview
        val cardViewDecline: CardView = binding.declinepushupCardview

        //clicklistener for Arms Category
        cardViewPushUps.setOnClickListener {
            action = ChestExercisesFragmentDirections.actionChestExercisesFragmentToWorkoutFragment(11)
            findNavController().navigate(action)
        }

        //clicklistener for Legs Category
        cardViewWide.setOnClickListener {
            action = ChestExercisesFragmentDirections.actionChestExercisesFragmentToWorkoutFragment(12)
            findNavController().navigate(action)
        }

        //clicklistener for Chest Category
        cardViewBurpees.setOnClickListener {
            action = ChestExercisesFragmentDirections.actionChestExercisesFragmentToWorkoutFragment(13)
            findNavController().navigate(action)
        }

        //clicklistener for Back Category
        cardViewIncline.setOnClickListener {
            action = ChestExercisesFragmentDirections.actionChestExercisesFragmentToWorkoutFragment(14)
            findNavController().navigate(action)
        }

        //clicklistener for Core Category
        cardViewDecline.setOnClickListener {
            action = ChestExercisesFragmentDirections.actionChestExercisesFragmentToWorkoutFragment(15)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}