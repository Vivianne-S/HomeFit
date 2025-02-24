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
import com.example.homefit.databinding.FragmentBackExercisesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BackExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BackExercisesFragment : Fragment() {
    //private var param1: String? = null
    //private var param2: String? = null

    private var _binding: FragmentBackExercisesBinding? = null
    private val binding get() = _binding!!
    lateinit var action : NavDirections

    companion object {
        fun newInstance() = BackExercisesFragment()
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
        _binding = FragmentBackExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI-element
        val cardViewSuper: CardView = binding.supermanCardview
        val cardViewCatCow: CardView = binding.catcowCardview
        val cardViewGood: CardView = binding.goodmorningCardview
        val cardViewReverseFlys: CardView = binding.reverseflysCardview
        val cardViewReversePlank: CardView = binding.reverseplankCardview

        cardViewSuper.setOnClickListener {
            action = BackExercisesFragmentDirections.actionBackExercisesFragmentToWorkoutFragment(16)
            findNavController().navigate(action)
        }
        cardViewGood.setOnClickListener {
            action = BackExercisesFragmentDirections.actionBackExercisesFragmentToWorkoutFragment(17)
            findNavController().navigate(action)
        }
        cardViewReversePlank.setOnClickListener {
            action = BackExercisesFragmentDirections.actionBackExercisesFragmentToWorkoutFragment(18)
            findNavController().navigate(action)
        }
        cardViewCatCow.setOnClickListener {
            action = BackExercisesFragmentDirections.actionBackExercisesFragmentToWorkoutFragment(19)
            findNavController().navigate(action)
        }
        cardViewReverseFlys.setOnClickListener {
            action = BackExercisesFragmentDirections.actionBackExercisesFragmentToWorkoutFragment(20)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}