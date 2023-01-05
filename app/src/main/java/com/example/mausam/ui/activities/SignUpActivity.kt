package com.example.mausam.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mausam.R
import com.example.mausam.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            registerUser()
        }
    }
    private fun registerUser(){
        val name=binding.etName.text.toString()
        val email=binding.etEmail.text.toString()
        val password=binding.etPassword.text.toString()
        if(validateForm(name,email,password )){
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        Toast.makeText(this@SignUpActivity, "registered", Toast.LENGTH_SHORT).show()
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity, "not successful", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        else{
            Toast.makeText(this@SignUpActivity, "you cannot register", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarSignUpActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)

        }

        binding.toolbarSignUpActivity.setNavigationOnClickListener { onBackPressed() }
    }
    private fun validateForm(name:String,email:String,password:String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please enter your name")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a valid password")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter an email")
                false
            }
            else -> {true}
        }
    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBar.show()
    }
    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}

