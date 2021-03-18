package com.pmirkelam.cointracker.ui.coins

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pmirkelam.cointracker.R
import com.pmirkelam.cointracker.data.Coin
import com.pmirkelam.cointracker.databinding.FragmentCoinBinding

class CoinRecyclerViewAdapter : RecyclerView.Adapter<CoinRecyclerViewAdapter.ViewHolder>() {

    private var list: List<Coin>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val coinBinding: FragmentCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.fragment_coin, viewGroup, false
        )
        return ViewHolder(coinBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.coinBinding.coin = list?.get(position)
    }

    var coinList: List<Coin>?
        get() = this.list
        set(value) {
            this.list = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(binding: FragmentCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        val coinBinding: FragmentCoinBinding = binding

    }
}