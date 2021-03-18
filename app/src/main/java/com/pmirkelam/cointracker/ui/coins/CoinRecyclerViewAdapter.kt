package com.pmirkelam.cointracker.ui.coins

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.databinding.FragmentCoinBinding

class CoinRecyclerViewAdapter(private val listener: CoinItemListener) :
    RecyclerView.Adapter<CoinRecyclerViewAdapter.ViewHolder>() {

    private var list: List<Coin>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val coinBinding: FragmentCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.fragment_coin, viewGroup, false
        )
        return ViewHolder(coinBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.coinBinding.coin = list?.get(position)
        list?.get(position)?.let { holder.bind(it) }
    }

    var coinList: List<Coin>?
        get() = this.list
        set(value) {
            this.list = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(
        binding: FragmentCoinBinding,
        private val listener: CoinItemListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var coin: Coin
        val coinBinding: FragmentCoinBinding = binding

        init {
            coinBinding.root.setOnClickListener(this)
        }

        fun bind(item: Coin) {
            this.coin = item
            coinBinding.coinItemName.text = item.name
            coinBinding.coinItemSymbol.text = item.symbol
//            Glide.with(coinBinding.root)
//                .load(item.image)
//                .transform(CircleCrop())
//                .into(coinBinding.coinItemIcon)
        }

        override fun onClick(v: View?) {
            listener.onClickedCoin(coin.id)
        }
    }

    interface CoinItemListener {
        fun onClickedCoin(coinId: String)
    }
}