package no.kristiania.coinhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.ItemCurrencyViewBinding
import no.kristiania.coinhub.models.CurrencyStats


class CurrencyListAdapter(
    private var list: List<CurrencyStats>,
    private val listener : (CurrencyStats) -> Unit
) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyViewHolder {

        return CurrencyViewHolder(ItemCurrencyViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int = list.size

    class CurrencyViewHolder(private val binding: ItemCurrencyViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(stats: CurrencyStats, listener : (CurrencyStats) -> Unit) {
            Picasso.get().load("https://static.coincap.io/assets/icons/${stats.Symbol.toLowerCase()}@2x.png").into(binding.currencyLogo)

            binding.currencyName.text = "${stats.Name}"
            binding.currencySymbol.text = "${stats.Symbol}"
            binding.currencyValue.text = "${stats.PriceUsd}"
            binding.changePercent.text = "${stats.ChangePercent24Hr}%"
            itemView.setOnClickListener { listener (stats) }

        }
    }

    fun update (newList: List<CurrencyStats>){
        list = newList
        notifyDataSetChanged()
    }
}