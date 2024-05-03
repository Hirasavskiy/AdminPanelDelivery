package com.example.adminpaneldelivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpaneldelivery.databinding.PendingOrderItemBinding

class PendingOrderAdapter(private val customerNames:ArrayList<String>, private val quantity:ArrayList<String>,
                          private val foodImage:ArrayList<Int>, private val context: Context): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


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
                orderedFoodImage.setImageResource(foodImage[position])
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
                        }
                        else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Заказ отправлен")
                        }
                    }
                }

            }

        }
        private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}