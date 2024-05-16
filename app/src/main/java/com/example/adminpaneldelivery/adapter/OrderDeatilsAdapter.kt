package com.example.adminpaneldelivery.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpaneldelivery.databinding.OrderDetailItemBinding

class OrderDeatilsAdapter(private var context: Context,
    private var foodNames: MutableList<String>,
                          private var foodImages: MutableList<String>,
                          private var foodQuantities: MutableList<Int>,
                          private var foodPrices: MutableList<String>,
    ): RecyclerView.Adapter<OrderDeatilsAdapter.OrderDeatailsViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDeatailsViewHolder {
        val binding = OrderDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDeatailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDeatailsViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = foodNames.size

    inner class OrderDeatailsViewHolder(private val binding: OrderDetailItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = foodNames[position]
                foodQuantity.text = foodQuantities[position].toString()
                val uriString = foodImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImage)
                foodPrice.text = foodPrices[position]
            }
        }

    }


}