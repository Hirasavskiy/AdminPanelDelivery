package com.example.adminpaneldelivery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpaneldelivery.adapter.AddItemsAdapter
import com.example.adminpaneldelivery.databinding.ActivityAllItemsBinding

class AllItemsActivity : AppCompatActivity() {
    private val binding: ActivityAllItemsBinding by lazy {
        ActivityAllItemsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val menuFoodName = listOf("Крабовые палочки", "Пельмени", "Мороженое")
        val menuItemPrice = listOf("2 р.", "3.50 р.", "2.50 р.")
        val menuImage = listOf(
            R.drawable.palochki,
            R.drawable.pelmenisochnye,
            R.drawable.toparbuz
        )
        val adapter = AddItemsAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice), ArrayList(menuImage))
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}