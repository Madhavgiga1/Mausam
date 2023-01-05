package com.example.mausam.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.mausam.R
import com.example.mausam.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = FirebaseAuth.getInstance().currentUser

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignIn.setOnClickListener {
            auth()
        }

    }
    private fun validate(email:String,password: String): Boolean {
        return when{
            TextUtils.isEmpty(email)->{
                false
            }
            TextUtils.isEmpty(password)->{
                false
            }
            else->{
                true
            }
        }
    }
    private fun auth() {
        val email=binding.etEmail.text.toString().trim { it <= ' ' }
        val password= binding.etPassword.text.toString().trim { it <= ' ' }
        if(validate(email,password)){
            signIn(email, password)

        }
        else{
            Toast.makeText(this@LoginActivity, "LoginFailed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarSignInActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarSignInActivity.setNavigationOnClickListener { onBackPressed() }
    }
}