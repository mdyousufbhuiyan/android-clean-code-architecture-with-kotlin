package com.example.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ItemLayoutBinding
import com.example.currencyconverter.model.ItemsModel

class CurrencyAdapter(private val mList: List<ItemsModel>) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemModel = mList[position]
        holder.tvCurrency.text = itemModel.currency
        holder.tvCountryName.text = itemModel.countryName
        holder.tvExchangeRate.text = "Ex Rate: ${itemModel.exchangeRate}"
        holder.tvEquivalentAmount.text ="Eq Amount: ${itemModel.equivalentAmount}"
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it text
    class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvCurrency: TextView = itemView.findViewById(R.id.tvCurrency)
        val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        val tvExchangeRate: TextView = itemView.findViewById(R.id.tvExchangeRate)
        val tvEquivalentAmount: TextView = itemView.findViewById(R.id.tvEquivalentAmount)
    }
}