package com.example.adminpaneldelivery

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpaneldelivery.databinding.ActivityAdminProfileBinding
import com.example.adminpaneldelivery.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.saveBtn.setOnClickListener {
            updateUserData()
        }
        setEditMode(false)

        binding.editBtn.setOnClickListener {
            setEditMode(!binding.name.isEnabled)
        }

        retrieveUserData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setEditMode(isEnabled: Boolean) {
        binding.name.isEnabled = isEnabled
        binding.address.isEnabled = isEnabled
        binding.email.isEnabled = isEnabled
        binding.number.isEnabled = isEnabled
        binding.password.isEnabled = isEnabled
        binding.saveBtn.isEnabled = isEnabled
        if (isEnabled) {
            binding.name.requestFocus()
        }
    }

    private fun updateUserData() {
        val updateName = binding.name.text.toString()
        val updateEmail = binding.email.text.toString()
        val updateAddress = binding.address.text.toString()
        val updatePassword = binding.password.text.toString()
        val currentUserUid = auth.currentUser?.uid

        if (currentUserUid != null) {
            val userReference = adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("address").setValue(updateAddress)
            userReference.child("password").setValue(updatePassword)
            userReference.child("email").setValue(updateEmail)
            Toast.makeText(this, "Обновление успешно", Toast.LENGTH_SHORT).show()

            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        } else {
            Toast.makeText(this, "Ошибка обновления", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userReference = adminReference.child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val ownerName = snapshot.child("name").getValue(String::class.java)
                        val email = snapshot.child("email").getValue(String::class.java)
                        val address = snapshot.child("address").getValue(String::class.java)  // Исправлен ключ
                        val password = snapshot.child("password").getValue(String::class.java)
                        setDataToTextView(ownerName, email, address, password)
                        Log.d("AdminProfile", "User data retrieved: $ownerName, $email, $address, $password")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("AdminProfile", "Error retrieving user data", error.toException())
                }
            })
        }
    }

    private fun setDataToTextView(ownerName: String?, email: String?, address: String?, password: String?) {
        binding.name.setText(ownerName)
        binding.email.setText(email)
        binding.address.setText(address)
        binding.password.setText(password)
    }
}
