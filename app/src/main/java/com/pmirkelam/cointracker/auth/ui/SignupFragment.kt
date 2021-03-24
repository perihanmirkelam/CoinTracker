package com.pmirkelam.cointracker.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.viewModel = viewModel
        navController = NavHostFragment.findNavController(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProgress()
        observeErrors()
        observeSignUpStatus()
        observeBackToLogin()
    }

    private fun observeSignUpStatus() {
        viewModel.isSignedUp.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(context, "Signed up successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToCoinListFragment()
                )
                viewModel.navigated()
            }
        })
    }

    private fun observeBackToLogin() {
        viewModel.backToLogin.observe(viewLifecycleOwner, {
            if (it) {
                navController.navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                )
                viewModel.navigated()
            }
        })
    }

    private fun observeErrors() {
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun observeProgress() {
        viewModel.showProgress.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
    }

    override fun onDestroy() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        super.onDestroy()

    }
}