package com.example.investmenttracker.screens.investment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.investmenttracker.R
import com.example.investmenttracker.models.Investment
import com.example.investmenttracker.databinding.ItemInvestmentBinding

class InvestmentAdapter(
    private var investments: MutableList<Investment> = mutableListOf(),
    private val onInvestmentClick: (Investment) -> Unit
) : RecyclerView.Adapter<InvestmentAdapter.InvestmentViewHolder>() {
    var selectedPosition = RecyclerView.NO_POSITION

    fun updateInvestments(newInvestments: List<Investment>) {
        Log.d("Adapter", "Updating investments: ${newInvestments.size}")
        investments = newInvestments.toMutableList()
        notifyDataSetChanged()
    }

    inner class InvestmentViewHolder(private val binding: ItemInvestmentBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                // Check if the item was already selected
                if (selectedPosition == layoutPosition) {
                    // Item was selected, now deselect it
                    selectedPosition = RecyclerView.NO_POSITION
                } else {
                    // New item selected
                    val previousSelected = selectedPosition
                    selectedPosition = layoutPosition
                    notifyItemChanged(previousSelected) // Deselect the previous item
                }
                notifyItemChanged(layoutPosition) // Reflect the change
                onInvestmentClick(investments[layoutPosition])
            }
        }

        fun bind(investment: Investment, selected: Boolean) {
            binding.investmentName.text = investment.investmentName
            binding.investmentAmount.text = investment.investmentAmount.toString()
            // Set the background color based on the selected state
            binding.root.setBackgroundColor(
                if (selected) itemView.context.resources.getColor(R.color.selected_investment_background)
                else itemView.context.resources.getColor(R.color.white)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestmentViewHolder {
        val binding = ItemInvestmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvestmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvestmentViewHolder, position: Int) {
        val investment = investments[position]
        val selected = position == selectedPosition
        holder.bind(investment, selected)
    }

    override fun getItemCount(): Int = investments.size

    // Call this method to clear selection from the fragment when needed
    fun clearSelection() {
        val previousSelected = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(previousSelected)
    }
}

