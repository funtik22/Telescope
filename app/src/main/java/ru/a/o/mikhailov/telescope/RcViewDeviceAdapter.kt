package ru.a.o.mikhailov.telescope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.a.o.mikhailov.telescope.databinding.ListItemBinding

class RcViewDeviceAdapter(private val listener: Listener) : ListAdapter <BluetoothDevice, RcViewDeviceAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)

        // this fun files rcItem
        fun setData(item: BluetoothDevice, listener: Listener) = with(binding){
            tvDeviceName.text = item.name
            tvDeviceMac.text = item.mac
            itemView.setOnClickListener {
                listener.onClick(item)
            }
        }

        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.list_item, parent, false))
            }
        }
    }


    class ItemComparator : DiffUtil.ItemCallback<BluetoothDevice>(){
        override fun areItemsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice): Boolean {
            return oldItem.mac == newItem.mac
        }

        override fun areContentsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    interface Listener{
        fun onClick(item: BluetoothDevice){

        }
    }
}