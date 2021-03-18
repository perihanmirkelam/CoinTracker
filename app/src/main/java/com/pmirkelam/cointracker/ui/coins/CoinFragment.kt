package com.pmirkelam.cointracker.ui.coins

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.FragmentCoinListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinFragment : Fragment(), CoinRecyclerViewAdapter.CoinItemListener {

    private lateinit var coinViewModel: CoinViewModel
    private lateinit var recyclerViewCoin: RecyclerView
    private lateinit var coinDataAdapter: CoinRecyclerViewAdapter
    private lateinit var binding: FragmentCoinListBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        coinViewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_list, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        binding.coinViewModel = coinViewModel
        recyclerViewCoin = binding.recyclerViewCoinList
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.stackFromEnd = true
        recyclerViewCoin.layoutManager = linearLayoutManager
        coinDataAdapter = CoinRecyclerViewAdapter(this)
        recyclerViewCoin.adapter = coinDataAdapter
        recyclerViewCoin.itemAnimator = DefaultItemAnimator()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinViewModel.coins.observe(viewLifecycleOwner, {
                coinList ->
            run {
                coinDataAdapter.coinList = coinList
                coinDataAdapter.notifyDataSetChanged()
                if (coinList.isNotEmpty()) {
                    recyclerViewCoin.smoothScrollToPosition(coinDataAdapter.itemCount - 1)
                }
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