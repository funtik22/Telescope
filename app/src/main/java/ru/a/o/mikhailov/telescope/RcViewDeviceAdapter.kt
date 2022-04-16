package ru.a.o.mikhailov.telescope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.a.o.mikhailov.telescope.databinding.ListItemBinding

class RcViewDeviceAdapter : ListAdapter <ListItem, RcViewDeviceAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)
        fun setData(item: ListItem) = with(binding){
            tvDeviceName.text = item.name
            tvDeviceMac.text = item.mac
        }

        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.list_item, parent, false))
            }
        }
    }


    class ItemComparator : DiffUtil.ItemCallback<ListItem>(){
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.mac == newItem.mac
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position))
    }
}