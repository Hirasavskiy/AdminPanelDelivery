package com.example.adminpaneldelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpaneldelivery.databinding.ActivityLoginBinding
import com.example.adminpaneldelivery.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    private var userName: String ?= null
    private var nameOfShop: String ?= null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //иницилизация Firebase auth
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.loginBtn.setOnClickListener {

            email = binding.emailEditView.text.toString().trim()
            password = binding.passwordEditView.text.toString().trim()

            if (email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            else{
                signInUserAccount(email, password)
            }

        }
        binding.doNotHaveAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signInUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show()
                updateUI(user)
            }
            else{
                /*auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show()
                        saveUserData()
                        updateUI(user)
                    }
                    else{
                        Toast.makeText(this, "Ошибка аунтефикации", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "signInUserAccount: Ошибка аунтефикации", task.exception)
                    }

                }*/
                Toast.makeText(this, "Ошибка аунтефикации", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData() {
        email = binding.emailEditView.text.toString().trim()
        password = binding.passwordEditView.text.toString().trim()

        val user = UserModel(userName, nameOfShop, email, password)
        val userId= FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}