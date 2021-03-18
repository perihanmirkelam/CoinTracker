package com.pmirkelam.cointracker.ui.coindetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.FragmentCoinDetailBinding

class CoinDetailFragment : Fragment() {

    companion object {
        fun newInstance() = CoinDetailFragment()
    }

    private lateinit var viewModel: CoinDetailViewModel
    private lateinit var binding: FragmentCoinDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CoinDetailViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_detail, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        binding.coinDetailViewModel = viewModel

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: observe selected coin detail
    }

}