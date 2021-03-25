package com.pmirkelam.cointracker.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.coins.data.Coin
import com.pmirkelam.cointracker.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.CoinItemListener {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recyclerViewCoin: RecyclerView
    private lateinit var coinsDataAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        viewModel.favorites.observe(viewLifecycleOwner, { coinDetailList ->
            val array = arrayListOf<Coin>()
            array.addAll(coinDetailList.map { Coin(it.id, it.name, it.symbol) })
            coinsDataAdapter.coinList = array
            binding.progressBar.visibility = View.GONE
        })
    }

    override fun onClickedCoin(coin: Coin) {
        NavHostFragment.findNavController(this)
            .navigate(FavoritesFragmentDirections.actionNavFavoritesToCoinDetailFragment(coin.id))
    }

    private fun initUI() {
        recyclerViewCoin = binding.recyclerViewFavoriteList
        coinsDataAdapter = FavoritesAdapter(this)
        recyclerViewCoin.adapter = coinsDataAdapter
    }
}


