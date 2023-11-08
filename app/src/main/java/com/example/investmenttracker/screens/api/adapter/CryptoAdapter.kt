package com.example.investmenttracker.screens.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.investmenttracker.R

class CryptoAdapter(private var coins: List<String>) : RecyclerView.Adapter<CryptoAdapter.CoinViewHolder>() {

    class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView_simple_list_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crypto, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.textView.text = coins[position]
    }

    override fun getItemCount() = coins.size

    fun updateData(newCoins: List<String>) {
        coins = newCoins
        notifyDataSetChanged()
    }
}
