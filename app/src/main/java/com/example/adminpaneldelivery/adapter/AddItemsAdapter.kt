package com.example.adminpaneldelivery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpaneldelivery.databinding.ProductItemBinding

class AddItemsAdapter(private val MenuItemName: ArrayList<String>, private val MenuItemPrice: ArrayList<String>, private val MenuItemImage:ArrayList<Int>):
    RecyclerView.Adapter<AddItemsAdapter.AddItemsViewHolder>() {

        private val itemQuantities = IntArray(MenuItemName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemsViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size

    inner class AddItemsViewHolder(private val binding:ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                nameItem.text = MenuItemName[position]
                priceItem.text = MenuItemPrice[position]
                foodImageView.setImageResource(MenuItemImage[position])
                quantityTextView.text = quantity.toString()

                minusBtn.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusBtn.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteBtn.setOnClickListener {
                    deleteQuantity(position)
                }
            }
        }

        private fun deleteQuantity(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemName.size)
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10){
                itemQuantities[position]++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1){
                itemQuantities[position]--
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

    }
}