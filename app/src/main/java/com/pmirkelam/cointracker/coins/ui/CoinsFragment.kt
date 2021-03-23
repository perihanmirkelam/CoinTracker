package com.pmirkelam.cointracker.coins.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.FragmentCoinsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CoinsFragment : Fragment() {

    private val viewModel: CoinsViewModel by viewModels()
    private lateinit var binding: FragmentCoinsListBinding
    private lateinit var recyclerViewCoin: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinsDataAdapter = CoinsPagingAdapter { item, extras ->
            val args = bundleOf("id" to item.id)
            findNavController().navigate(
                R.id.action_coinListFragment_to_coinDetailFragment, args,
                null, extras
            )
        }.apply {
            addLoadStateListener {
                binding.progressBar.visibility =
                    if (it.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            }
        }

        recyclerViewCoin = binding.recyclerViewCoinList
        recyclerViewCoin.adapter = coinsDataAdapter

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                coinsDataAdapter.submitData(pagingData)
            }
        }
    }

}

