package com.wizeline.realtimestocks.ui.featuredstocks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wizeline.realtimestocks.databinding.FragmentFeaturedStocksBinding
import com.wizeline.realtimestocks.stock.Stock
import com.wizeline.realtimestocks.ui.adapter.StockAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FeaturedStocksFragment : Fragment() {

    private var _binding: FragmentFeaturedStocksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FeaturedStocksViewModel by viewModels()

    private val itemAdapter: StockAdapter = StockAdapter()

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeaturedStocksBinding.inflate(inflater)
        _binding!!.recyclerViewStockList.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        viewModel.stocks.observe(viewLifecycleOwner) { stocks ->
            onStocks(stocks)
        }
        return binding.root
    }

    private fun onStocks(stocks: List<Stock>) {
        itemAdapter.updateList(stocks)
        Log.d(javaClass.simpleName, "$stocks")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
