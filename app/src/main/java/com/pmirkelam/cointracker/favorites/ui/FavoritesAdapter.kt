package com.pmirkelam.cointracker.favorites.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.coins.data.Coin
import com.pmirkelam.cointracker.databinding.ListItemCoinBinding

class FavoritesAdapter(private val listener: CoinItemListener) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var list = ArrayList<Coin>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val coinBinding: ListItemCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_item_coin, viewGroup, false
        )
        return ViewHolder(coinBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    var coinList: ArrayList<Coin>
        get() = this.list
        set(value) {
            this.list = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(
        binding: ListItemCoinBinding,
        private val listener: CoinItemListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var coin: Coin
        val coinBinding: ListItemCoinBinding = binding

        init {
            coinBinding.root.setOnClickListener(this)
        }

        fun bind(item: Coin) {
            this.coin = item
            coinBinding.coin = item
        }

        override fun onClick(v: View?) {
            listener.onClickedCoin(coin)
        }
    }

    interface CoinItemListener {
        fun onClickedCoin(coin: Coin)
    }
}