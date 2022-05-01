package com.example.studykotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.studykotlin.databinding.ActivityBoxBinding
import com.example.studykotlin.model.Options
import java.lang.IllegalArgumentException

class BoxActivity : AppCompatActivity() {
    private lateinit var binding:ActivityBoxBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        options = intent.getParcelableExtra(BoxSelectionActivity.EXTRA_OPTIONS) ?:
                throw IllegalArgumentException("Can't launch BoxSelectionActivity without options")

        binding.toMainMenuButton.setOnClickListener { onToMainMenuPressed() }
    }

    private fun onToMainMenuPressed() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        setResult(RESULT_OK, intent)
        startActivity(intent)
    }
}