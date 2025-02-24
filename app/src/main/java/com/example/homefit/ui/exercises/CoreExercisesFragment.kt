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
import com.example.homefit.databinding.FragmentChestExercisesBinding
import com.example.homefit.databinding.FragmentCoreExercisesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoreExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoreExercisesFragment : Fragment() {
    private var _binding: FragmentCoreExercisesBinding? = null
    private val binding get() = _binding!!
    lateinit var action : NavDirections

    companion object {
        fun newInstance() = CoreExercisesFragment()
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
        _binding = FragmentCoreExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI-element
        val cardViewPushUps: CardView = binding.plankCardview
        val cardViewWide: CardView = binding.crunchesCardview
        val cardViewBurpees: CardView = binding.bicyclecrunchesCardview
        val cardViewIncline: CardView = binding.legraisesCardview
        val cardViewDecline: CardView = binding.heeltapsCardview

        //clicklistener for Arms Category
        cardViewPushUps.setOnClickListener {
            action = CoreExercisesFragmentDirections.actionCoreExercisesFragmentToWorkoutFragment(21)
            findNavController().navigate(action)
        }

        //clicklistener for Legs Category
        cardViewWide.setOnClickListener {
            action = CoreExercisesFragmentDirections.actionCoreExercisesFragmentToWorkoutFragment(22)
            findNavController().navigate(action)
        }

        //clicklistener for Chest Category
        cardViewBurpees.setOnClickListener {
            action = CoreExercisesFragmentDirections.actionCoreExercisesFragmentToWorkoutFragment(23)
            findNavController().navigate(action)
        }

        //clicklistener for Back Category
        cardViewIncline.setOnClickListener {
            action = CoreExercisesFragmentDirections.actionCoreExercisesFragmentToWorkoutFragment(24)
            findNavController().navigate(action)
        }

        //clicklistener for Core Category
        cardViewDecline.setOnClickListener {
            action = CoreExercisesFragmentDirections.actionCoreExercisesFragmentToWorkoutFragment(25)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}