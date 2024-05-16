package com.example.adminpaneldelivery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpaneldelivery.adapter.OrderDeatilsAdapter
import com.example.adminpaneldelivery.databinding.ActivityOrderDetailsBinding
import com.example.adminpaneldelivery.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root) // Используем binding.root вместо setContentView(R.layout.activity_order_details)
        binding.backBtn.setOnClickListener {
            finish()
        }
        getDataFromIntent()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails?
        receivedOrderDetails?.let { orderDetails ->
            userName = orderDetails.userName
            foodNames = orderDetails.foodNames as ArrayList<String>
            foodImages = orderDetails.foodImages as ArrayList<String>
            foodQuantity = orderDetails.foodQuantities as ArrayList<Int>
            address = orderDetails.address
            phoneNumber = orderDetails.phoneNumber
            foodPrices = orderDetails.foodPrices as ArrayList<String>
            totalPrice = orderDetails.totalPrice

            setUserDetails()
            setAdapter()
        }
    }

    private fun setUserDetails() {
        binding.name.text = userName
        binding.adress.text = address
        binding.phone.text = phoneNumber
        binding.total.text = totalPrice
    }

    private fun setAdapter() {
        binding.orderDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDeatilsAdapter(this, foodNames, foodImages, foodQuantity, foodPrices)
        binding.orderDetailRecyclerView.adapter = adapter
    }
}

