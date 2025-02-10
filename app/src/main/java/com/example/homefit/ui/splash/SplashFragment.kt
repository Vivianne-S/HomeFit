package com.example.homefit.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSplashBinding
import com.example.homefit.ui.splash.SplashViewModel


class SplashFragment : Fragment() {

    // ViewBinding för att hantera UI-element i fragmentet
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    // ViewModel för att hantera logik och UI-uppdateringar
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observera om "Journey"-texten ska visas
        viewModel.showJourneyText.observe(viewLifecycleOwner) { show ->
            binding.tvJourney.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Observera om "With"-texten ska visas
        viewModel.showWithText.observe(viewLifecycleOwner) { show ->
            binding.tvWith.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Observera om "HomeFit"-texten ska visas
        viewModel.showHomeFitText.observe(viewLifecycleOwner) { show ->
            binding.tvHomeFit.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Navigera till inloggningsskärmen när användaren klickar på skärmen
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Undvik minnesläckor
    }
}
