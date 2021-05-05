package no.kristiania.coinhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.PortfolioItemBinding
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.models.RateStats
import java.math.RoundingMode


class PortfolioAdapter() :
    RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

        private val portfolioList = mutableListOf<Transaction>()

        class ViewHolder(val binding: PortfolioItemBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind( transaction : Transaction)  {

                binding.textViewCryptoAmount.text = transaction.volume.toString()
                val symbol = transaction.symbol
                Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.imageViewCrypto)

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val holder = ViewHolder(PortfolioItemBinding.inflate(LayoutInflater.from(parent.context)))
            return holder
        }

        override fun getItemCount(): Int {
            return portfolioList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(portfolioList[position])
        }

        fun setPortfolioList(list: List<Transaction>) {
            portfolioList.clear()
            portfolioList.addAll(list)
            notifyDataSetChanged()
        }

    }
