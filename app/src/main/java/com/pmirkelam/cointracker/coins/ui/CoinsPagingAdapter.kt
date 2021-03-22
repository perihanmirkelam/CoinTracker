package com.pmirkelam.cointracker.coins.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.coins.data.Coin
import com.pmirkelam.cointracker.databinding.ListItemCoinBinding

class CoinsPagingAdapter(
    private val clickListener: (Coin, Navigator.Extras) -> Unit
) : PagingDataAdapter<Coin, CoinsPagingAdapter.CoinViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding: ListItemCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_coin, parent, false
        )
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        coin?.let {
            holder.bindTo(it, clickListener)
        }
    }

    class CoinViewHolder(
        private val binding: ListItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(
            item: Coin,
            clickListener: (Coin, Navigator.Extras) -> Unit
        ) {
            binding.coin = item
            ViewCompat.setTransitionName(binding.root, item.id)
            itemView.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.root to item.id
                )
                clickListener(item, extras)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(
                oldItem: Coin,
                newItem: Coin
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Coin,
                newItem: Coin
            ) = oldItem == newItem
        }
    }
}