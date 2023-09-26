package com.wizeline.realtimestocks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wizeline.realtimestocks.databinding.ItemListBinding
import com.wizeline.realtimestocks.stock.Stock
import com.wizeline.realtimestocks.util.formatAsCurrency

class StockAdapter : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private var stockList: List<Stock> = emptyList()

    class StockViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Stock) {
            with(binding) {
                stockCompany.text = item.companyName
                stockPrice.text = item.price.formatAsCurrency()
                stockTicker.text = item.ticker
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StockViewHolder(ItemListBinding.inflate(layoutInflater, parent, false))

    }

    override fun getItemCount(): Int = stockList.size

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val item = stockList[position]
        holder.bind(item)

    }

    fun setList(list: List<Stock>) {
        val size = list.size
        stockList = list
        //notifyItemRangeChanged(0, size)
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Stock>) {
        val diffResult = DiffUtil.calculateDiff(StockDiffCallback(stockList, newList))
        stockList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private class StockDiffCallback(
        private val oldList: List<Stock>,
        private val newList: List<Stock>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].companyName == newList[newItemPosition].companyName
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}