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
import com.pmirkelam.cointracker.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        navController = NavHostFragment.findNavController(this)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoginStatus()
        observeErrors()
        observeProgress()
        observeSignUpChoice()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun observeLoginStatus() {
        // Skip login screen if logged in once
        viewModel.isLoggedIn.observe(viewLifecycleOwner, {
            if (it) {
                navController.navigate(R.id.action_loginFragment_to_coinListFragment)
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

    private fun observeSignUpChoice() {
        viewModel.signUpNeeded.observe(viewLifecycleOwner, {
            if (it) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment)
                viewModel.navigated()
            }
        })
    }

    override fun onDestroy() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        super.onDestroy()

    }

}