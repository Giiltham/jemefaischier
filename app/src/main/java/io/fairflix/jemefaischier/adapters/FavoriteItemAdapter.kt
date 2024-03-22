package io.fairflix.jemefaischier.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.fairflix.jemefaischier.databinding.FavoriteItemLayoutBinding
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.utils.OnItemClickListener
import io.fairflix.jemefaischier.utils.OnItemDeleteListener

class FavoriteItemAdapter  {
    class ItemAdapter(
        private val datalist :MutableList<Favorite>,
        private val onItemClickListener: OnItemClickListener<Favorite>,
        private val onItemDeleteListener: OnItemDeleteListener<Favorite>
    )
        : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
        class ItemHolder(binding: FavoriteItemLayoutBinding, deleteCallback : (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

            private val binding : FavoriteItemLayoutBinding

            init {
                this.binding = binding
                binding.deleteBtn.setOnClickListener {
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


            return ItemHolder(binding, ::deleteCallback)
        }

        override fun onBindViewHolder(
            holder: ItemHolder,
            position: Int
        ) {
            holder.bind(datalist[position].name, datalist[position], onItemClickListener)
        }

        override fun getItemCount(): Int {
            return this.datalist.size
        }

        private fun deleteCallback(position : Int){
            val el = datalist.removeAt(position)
            notifyItemRemoved(position)
            onItemDeleteListener.onItemDelete(el)
        }

    }
}


