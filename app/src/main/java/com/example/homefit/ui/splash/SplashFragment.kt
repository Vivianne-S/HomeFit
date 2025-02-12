package com.example.homefit.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    // Binding för att hantera fragmentets layout från xml
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.let { binding ->
            // Döljer texten från början för att visa dom en efter en med en handler
            binding.tvJourney.visibility = View.INVISIBLE
            binding.tvWith.visibility = View.INVISIBLE
            binding.tvHomeFit.visibility = View.INVISIBLE

            val handler = Handler(Looper.getMainLooper())

            // Visar Journey texten efter 1.5 sekunder
            handler.postDelayed({
                binding.tvJourney.visibility = View.VISIBLE
            }, 1500)

            // Visar With texten efter 2.1 sekunder
            handler.postDelayed({
                binding.tvWith.visibility = View.VISIBLE
            }, 2100)

            // Visar HomeFit texten efter 2.6 sekunder
            handler.postDelayed({
                binding.tvHomeFit.visibility = View.VISIBLE
            }, 2600)

            // Navigerar till inloggningssidan när användaren klickar på skärmen
            binding.root.setOnClickListener {
                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            }
        }
    }

    // Rensar upp bindingen för att förhindra minnesläckor när vyn förstörs
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
