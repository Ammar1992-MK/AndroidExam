package no.kristiania.coinhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.PortfolioItemBinding
import no.kristiania.coinhub.models.CurrencyStats
import java.math.RoundingMode


class PortfolioAdapter(val lambdaFunction: (CurrencyStats) -> Unit) :
    RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

        private val portfolioList = mutableListOf<CurrencyStats>()

        class ViewHolder(val binding: PortfolioItemBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind(stats: CurrencyStats, listener : (CurrencyStats) -> Unit)  {
                Picasso.get().load("https://static.coincap.io/assets/icons/${stats.Symbol.toLowerCase()}@2x.png").into(binding.imageViewCrypto)

                binding.currencyName.text = "${stats.Name}"
                binding.imageViewCrypto.imageMatrix = "${stats.Symbol}"
                binding.currencyValue.text = "$ ${stats.PriceUsd.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()}"
                binding.changePercent.text = "${stats.ChangePercent24Hr.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()}%"

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

    fun setPortfolioList(list: List<CurrencyStats>) {
        portfolioList.clear()
        portfolioList.addAll(list)
        notifyDataSetChanged()
    }

    }
