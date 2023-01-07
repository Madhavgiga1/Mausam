package com.example.mausam.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.mausam.databinding.ActivityIntroScreenBinding

class IntroScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivityIntroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityIntroScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        binding.SIGNUPBUTTON.setOnClickListener {
            startActivity(Intent(this@IntroScreenActivity, SignUpActivity::class.java))
        }
        binding.Loginbutton.setOnClickListener {
            startActivity(Intent(this@IntroScreenActivity, LoginActivity::class.java))
        }
    }
}