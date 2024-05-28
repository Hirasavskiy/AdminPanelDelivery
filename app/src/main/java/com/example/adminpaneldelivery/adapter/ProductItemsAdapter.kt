package com.example.adminpaneldelivery.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpaneldelivery.databinding.ProductItemBinding
import com.example.adminpaneldelivery.model.AllProduct
import com.google.firebase.database.DatabaseReference

class ProductItemsAdapter(
    private val context: Context,
    private val productList: ArrayList<AllProduct>,
    databaseReference: DatabaseReference,
    private val onDeleteClickListener:(position: Int) -> Unit
):
    RecyclerView.Adapter<ProductItemsAdapter.AddItemsViewHolder>() {

        private val itemQuantities = IntArray(productList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemsViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = productList.size

    inner class AddItemsViewHolder(private val binding:ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val productItem = productList[position]
                val uriString = productItem.foodImage
                val uri = Uri.parse(uriString)
                nameItem.text = productItem.foodName
                priceItem.text = productItem.foodPrice
                Glide.with(context).load(uri).into(foodImageView)
                quantityTextView.text = quantity.toString()

                deleteBtn.setOnClickListener {
                    onDeleteClickListener(position)
                }
            }
        }

        private fun deleteQuantity(position: Int) {
            productList.removeAt(position)
            productList.removeAt(position)
            productList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, productList.size)
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