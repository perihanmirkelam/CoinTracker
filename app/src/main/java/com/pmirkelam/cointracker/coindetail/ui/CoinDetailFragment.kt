package com.pmirkelam.cointracker.coindetail.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.FragmentCoinDetailBinding
import com.pmirkelam.cointracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val viewModel: CoinDetailViewModel by viewModels()
    private lateinit var binding: FragmentCoinDetailBinding
    private var isFavorite = false
    private lateinit var favoriteItem: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_detail, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.get("coin_id")?.let { coinId -> viewModel.start(coinId as String) }
        observeCoinDetail()
        observeFavoriteStatus()
    }

    private fun observeFavoriteStatus(){
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            isFavorite = it
            refreshIcons()
        })
    }

    private fun observeCoinDetail(){
        viewModel.coinDetail.observe(viewLifecycleOwner, {
            when (it?.status) {
                Resource.Status.SUCCESS -> {
                    binding.coin = it.data
                    binding.progressBar.visibility = View.GONE
                    it.let {
                        Glide.with(binding.root)
                            .load(it.data?.image?.url)
                            .into(binding.imageCoinDetail)
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


    private fun refreshIcons() {
        val icon = if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        if (this::favoriteItem.isInitialized) favoriteItem.setIcon(icon)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.coin_detail_menu, menu)
        favoriteItem = menu.findItem(R.id.action_add_favorites)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_favorites -> {
                Toast.makeText(
                    context,
                    if (isFavorite) "Removing from favorites..." else "Adding to Favorites...",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.favoriteButtonClicked()
            }
            R.id.action_set_interval -> {
                viewModel.setRefreshInterval()
                Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
            }
            R.id.action_refresh -> {
                viewModel.refresh()
                Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
