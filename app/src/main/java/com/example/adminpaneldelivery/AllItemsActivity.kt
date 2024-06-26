package com.example.adminpaneldelivery

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpaneldelivery.adapter.ProductItemsAdapter
import com.example.adminpaneldelivery.databinding.ActivityAllItemsBinding
import com.example.adminpaneldelivery.model.AllProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemsActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var productItems: ArrayList<AllProduct> = ArrayList()
    private val binding: ActivityAllItemsBinding by lazy {
        ActivityAllItemsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveProductItem()

        binding.backBtn.setOnClickListener{
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun retrieveProductItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("product")
        foodRef.addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                productItems.clear()

                for(foodSnapshot in snapshot.children){
                    val productItem = foodSnapshot.getValue(AllProduct::class.java)
                    productItem?.let {
                        productItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = ProductItemsAdapter(this@AllItemsActivity, productItems, databaseReference){position ->
            deleteProductItems(position)
        }
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.menuRecyclerView.adapter = adapter
    }

    private fun deleteProductItems(position: Int) {
        if (position >= 0 && position < productItems.size) {
            val productToDelete = productItems[position]
            val productItemKey = productToDelete.key

            if (productItemKey != null) { // Проверка на null перед использованием ключа
                val foodProductReference = database.reference.child("product").child(productItemKey)
                foodProductReference.removeValue().addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        productItems.removeAt(position)
                        binding.menuRecyclerView.adapter?.notifyItemRemoved(position)
                    }
                    else{
                        Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Log.e("DeleteProductItems", "Ключ равен null")
            }
        } else {
            Log.e("DeleteProductItems", "Попытка удаления элемента вне диапазона: position = $position")
        }
    }


}