package com.example.homefit.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSignInBinding
import com.example.homefit.ui.signin.SignInViewModel


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hantera inloggning
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Kontrollera om email eller lösen är tomt
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Anropa sign-in funktionen från ViewModel
            viewModel.signIn(email, password)
        }

        // Navigerar till registreringssidan
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // Navigerar till glömt lösenord-sidan
        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        // Kollar om användaren är autentiserad
        viewModel.isAuthenticated.observe(viewLifecycleOwner) { isAuthenticated ->
            if (isAuthenticated) {


                //  homefragment behöver skapas så man tar sig vidare när man loggat in!

                // Navigera till huvudsidan om inloggningen är framgångsrik, homefragment behöver skapas!
              //findNavController().navigate(R.id.action_signInFragment_to_homeFragment)




            } else {
                // Visa felmeddelande om inloggningen misslyckas
                Toast.makeText(requireContext(), "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Kollar felmeddelanden från ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
