package com.pmirkelam.cointracker.coindetail.ui

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.databinding.DialogSetIntervalBinding
import com.pmirkelam.cointracker.databinding.FragmentCoinDetailBinding
import com.pmirkelam.cointracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val viewModel: CoinDetailViewModel by viewModels()
    private lateinit var binding: FragmentCoinDetailBinding
    private var isFavorite = false
    private lateinit var favoriteItem: MenuItem
    private var dialog: Dialog? = null

    private lateinit var dialogBinding: DialogSetIntervalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_detail, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        dialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_set_interval, null, false
        )
        binding.viewModel = viewModel
        dialogBinding.viewModel = viewModel
        dialog = Dialog(requireContext())
        dialog?.setContentView(dialogBinding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.get("coin_id")?.let { coinId -> viewModel.start(coinId as String) }
        observeCoinDetail()
        observeFavoriteStatus()
        observeIntervalStatus()
    }

    private fun observeCoinDetail() {
        viewModel.coinDetail.observe(viewLifecycleOwner, {
            when (it?.status) {
                Resource.Status.SUCCESS -> {
                    binding.coin = it.data
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { detail ->
                        loadImage(detail.image?.url)
                        convertHtml(detail.description?.en)
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

    private fun observeFavoriteStatus() {
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            isFavorite = it
            if (this::favoriteItem.isInitialized) favoriteItem.setIcon(
                if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            )
        })
    }

    private fun observeIntervalStatus() {
        viewModel.isInternalDialogActive.observe(viewLifecycleOwner, {
            if (!it) dialog?.hide()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.coin_detail_menu, menu)
        favoriteItem = menu.findItem(R.id.action_add_favorites)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_favorites -> addToFavorites()
            R.id.action_set_interval -> dialog?.show()
            R.id.action_refresh -> viewModel.refresh()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun convertHtml(text: String?){
        val toHtml = text?.replace("\\n", "<br />")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.textDescription.text = Html.fromHtml(toHtml, Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.textDescription.text = text
        }
    }

    private fun loadImage(url: String?) =
        Glide.with(binding.root)
            .load(url)
            .into(binding.imageCoinDetail)


    private fun addToFavorites() {
        Toast.makeText(
            context,
            if (isFavorite) "Removing from favorites..." else "Adding to Favorites...",
            Toast.LENGTH_LONG
        ).show()
        viewModel.favoriteButtonClicked()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
