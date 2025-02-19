package com.example.homefit.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSignUpBinding
import com.example.homefit.ui.viewmodelauth.AuthViewModel

class SignUpFragment : Fragment() {

    // Binding för att hantera fragmentets layout från xml
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    // LottieAnimationView
    private lateinit var lottieAnimationView: LottieAnimationView

    // AuthViewModel för logiken / felmedelanden
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hämta LottieAnimationView
        lottieAnimationView = binding.lottieAnimationView

        // Starta animationen
        lottieAnimationView.playAnimation()
        // Observera Toast meddelanden från AuthViewModel
        authViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Klicklyssnare för registreringsknappen som hämtar e-post och lösenord
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val name = binding.etUsername.text.toString().trim()

            // Anropa signUp funktionen från AuthViewModel
            authViewModel.signUp(email, password, name) { success, errorMessage ->
                if (success) {
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                }
            }
        }

        // Navigera till inloggningssidan via länken Already have an account?
        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    // Rensar upp bindingen för att förhindra minnesläckor när vyn förstörs
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
