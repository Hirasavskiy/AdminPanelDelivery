package com.example.adminpaneldelivery.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpaneldelivery.databinding.PendingOrderItemBinding

class PendingOrderAdapter(private val context: Context, private val customerNames: MutableList<String>, private val quantity:MutableList<String>,
                          private val foodImage: MutableList<String>,
                          private val itemClicked: onItemClicked,
): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

interface onItemClicked{
    fun onItemClickListener(position: Int)
    fun onItemAcceptClickListener(position: Int)
    fun onItemDispatchClickListener(positiom: Int)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class PendingOrderViewHolder(private val binding: PendingOrderItemBinding): RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                pendingOrder.text = quantity[position]
                var uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderedFoodImage)
                orderAcceptBtn.apply {
                    if(!isAccepted){
                        text = "Принято"
                    }
                    else{
                        text = "Отправлено"
                    }
                    setOnClickListener {
                        if(!isAccepted){
                            text = "Отправлено"
                            isAccepted = true
                            showToast("Заказ принят")
                            itemClicked.onItemAcceptClickListener(position)
                        }
                        else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Заказ отправлен")
                            itemClicked.onItemDispatchClickListener(position)
                        }
                    }
                }
                itemView.setOnClickListener{
                    itemClicked.onItemClickListener(position)
                }

            }

        }
        private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}