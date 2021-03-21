package com.pmirkelam.cointracker.ui.coins

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.FragmentCoinsListBinding
import com.pmirkelam.cointracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsFragment : Fragment(), CoinsRecyclerViewAdapter.CoinItemListener {
    
    private val coinsViewModel: CoinsViewModel by viewModels()
    private lateinit var binding: FragmentCoinsListBinding
    private lateinit var recyclerViewCoin: RecyclerView
    private lateinit var coinsDataAdapter: CoinsRecyclerViewAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coins_list, container, false)
        return binding.root
    }

    private fun initUI() {
        binding.lifecycleOwner = this
        binding.coinViewModel = coinsViewModel
        recyclerViewCoin = binding.recyclerViewCoinList
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.stackFromEnd = true
        recyclerViewCoin.layoutManager = linearLayoutManager
        coinsDataAdapter = CoinsRecyclerViewAdapter(this)
        recyclerViewCoin.adapter = coinsDataAdapter
        recyclerViewCoin.itemAnimator = DefaultItemAnimator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        coinsViewModel.coins.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        coinsDataAdapter.coinList = ArrayList(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedCoin(coinId: String) {
        findNavController().navigate(
            R.id.action_coinListFragment_to_coinDetailFragment,
            bundleOf("id" to coinId)
        )
    }


}
