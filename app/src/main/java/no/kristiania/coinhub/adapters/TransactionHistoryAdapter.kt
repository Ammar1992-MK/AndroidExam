package no.kristiania.coinhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.TransactionHistoryItemBinding
import no.kristiania.coinhub.entities.TransactionHistory
import java.math.RoundingMode


class TransactionHistoryAdapter() :
        RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

        private val transactionHistoryList = mutableListOf<TransactionHistory>()

        class ViewHolder(val binding: TransactionHistoryItemBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind( transactionHistory: TransactionHistory)  {

                binding.transactionItemUsdPoints.text = transactionHistory.rate!!.toBigDecimal().setScale(3, RoundingMode.UP).toDouble().toString()
                binding.transactionItemCurrencySymbol.text = transactionHistory.symbol.toString()
                binding.transactionItemCurrencyVolume.text = transactionHistory.volume!!.toBigDecimal().setScale(5, RoundingMode.UP).toDouble().toString()
                binding.transactionsItemBoughtSold.text = transactionHistory.type.toString()
                val symbol = transactionHistory.symbol
                Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.transactionItemImage)

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val holder = ViewHolder(TransactionHistoryItemBinding.inflate(LayoutInflater.from(parent.context)))
            return holder
        }

        override fun getItemCount(): Int {
            return transactionHistoryList.size
        }

        override fun onBindViewHolder(holder: TransactionHistoryAdapter.ViewHolder, position: Int) {
            holder.bind(transactionHistoryList[position])
        }

        fun setTransactionHistoryList(list: List<TransactionHistory>) {
            transactionHistoryList.clear()
            transactionHistoryList.addAll(list)
            notifyDataSetChanged()
        }

}