package org.the_chance.honeymart.ui.feature.authentication

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentAuthBinding

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_auth
    override val viewModel: AuthViewModel by viewModels()

    override fun setup() {
        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_signupFragment)
        }
        binding.textViewLogin.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }
    }

}