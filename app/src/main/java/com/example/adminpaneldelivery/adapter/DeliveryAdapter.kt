package com.example.adminpaneldelivery.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpaneldelivery.databinding.DeliveryItemBinding

class DeliveryAdapter(private val customerNames: MutableList<String>, private val moneyStatus: MutableList<Boolean>) :
    RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        Log.d("DeliveryAdapter", "Item count: ${customerNames.size}")
        return customerNames.size
    }

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                statusMoney.text = if (moneyStatus[position]) "Получено" else "Не получено"

                val colorMark = if (moneyStatus[position]) Color.GREEN else Color.RED
                statusMoney.setTextColor(colorMark)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMark)
            }
            Log.d("DeliveryViewHolder", "Binding position: $position")
        }
    }
}