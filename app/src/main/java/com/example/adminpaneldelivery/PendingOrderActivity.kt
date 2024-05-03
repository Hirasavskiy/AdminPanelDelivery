package com.example.adminpaneldelivery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpaneldelivery.adapter.DeliveryAdapter
import com.example.adminpaneldelivery.adapter.PendingOrderAdapter
import com.example.adminpaneldelivery.databinding.ActivityPendingOrderBinding
import com.example.adminpaneldelivery.databinding.PendingOrderItemBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
        val orderedCustomerName = arrayListOf(
            "Иван Иванов",
            "Петр Петров",
            "Евгения Евгеньева"
        )
        val orderedQuantity = arrayListOf(
            "4",
            "7",
            "5"
        )
        val orderedFoodImage = arrayListOf(R.drawable.palochki, R.drawable.pelmenisochnye, R.drawable.toparbuz)
        val adapter = PendingOrderAdapter(orderedCustomerName, orderedQuantity, orderedFoodImage, this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}