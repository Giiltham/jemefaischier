package io.fairflix.jemefaischier.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import io.fairflix.jemefaischier.databinding.FavoriteItemLayoutBinding
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.utils.OnItemClickListener
import io.fairflix.jemefaischier.utils.OnItemDeleteListener

public class FavoriteItemAdapter  {
    class ItemAdapter(val datalist :MutableList<Favorite>,
                      val onItemClickListener: OnItemClickListener<Favorite>,
                      val onItemDeleteListener: OnItemDeleteListener<Favorite>
    )
        : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
        class ItemHolder(binding: FavoriteItemLayoutBinding, deleteCallback : (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

            val binding : FavoriteItemLayoutBinding
            private lateinit var item : Favorite

            init {
                this.binding = binding
                binding.deleteBtn.setOnClickListener() {
                    deleteCallback(adapterPosition)
                }
            }

            fun bind(title: String, item: Favorite, onItemClickListener: OnItemClickListener<Favorite>){
                binding.title.text = title
                binding.card.setOnClickListener {
                    onItemClickListener.onItemClick(item)
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ItemHolder {
            val binding = FavoriteItemLayoutBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)


            val itemHolder = ItemHolder(binding, ::deleteCallback)
            return itemHolder
        }

        override fun onBindViewHolder(
            holder: ItemHolder,
            position: Int
        ) {
            holder.bind(datalist.get(position).name,datalist.get(position), onItemClickListener)
        }

        override fun getItemCount(): Int {
            return this.datalist.size
        }

        fun deleteCallback(position : Int){
            val el = datalist.removeAt(position)
            notifyItemRemoved(position)
            onItemDeleteListener.onItemDelete(el)
        }

    }
}


