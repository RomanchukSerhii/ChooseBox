package com.example.studykotlin

import android.os.Bundle
import com.example.studykotlin.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.descriptionVersionNameTextView.text = BuildConfig.VERSION_NAME
        binding.descriptionVersionCodeTextView.text = BuildConfig.VERSION_CODE.toString()
        binding.okButton.setOnClickListener { onOkPressed() }
    }

    private fun onOkPressed() {
        finish()
    }
}