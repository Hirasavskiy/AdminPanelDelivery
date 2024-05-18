package com.example.adminpaneldelivery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpaneldelivery.adapter.OrderDeatilsAdapter
import com.example.adminpaneldelivery.databinding.ActivityOrderDetailsBinding
import com.example.adminpaneldelivery.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
    private var adminAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
        retrieveAdminAddress()
        getDataFromIntent()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.route.setOnClickListener {
            openGoogleMaps()
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

    private fun retrieveAdminAddress() {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserUid != null) {
            val userReference = FirebaseDatabase.getInstance().getReference("user").child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        adminAddress = snapshot.child("address").getValue(String::class.java)
                        Log.d("OrderDetailsActivity", "Admin address retrieved: $adminAddress")
                    } else {
                        Log.e("OrderDetailsActivity", "Admin address not found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("OrderDetailsActivity", "Error retrieving admin address", error.toException())
                }
            })
        }
    }

    private fun openGoogleMaps() {
        Log.d("OrderDetailsActivity", "Customer address: $address")
        Log.d("OrderDetailsActivity", "Admin address: $adminAddress")

        if (address != null && adminAddress != null) {
            val uri = "https://www.google.com/maps/dir/?api=1&origin=${Uri.encode(adminAddress)}&destination=${Uri.encode(address)}&travelmode=driving"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        } else {
            Toast.makeText(this, "Failed to retrieve addresses", Toast.LENGTH_SHORT).show()
        }
    }
}
