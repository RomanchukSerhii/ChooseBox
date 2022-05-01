package com.example.studykotlin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.studykotlin.databinding.ActivityMenuBinding
import com.example.studykotlin.model.Options
import java.lang.IllegalArgumentException

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var options: Options

    private val startResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            options = it.data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS) ?:
                    throw IllegalArgumentException("Can't get the update data from options activity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            openBoxButton.setOnClickListener { onOpenBoxPressed() }
            optionsButton.setOnClickListener { onOptionsPressed() }
            aboutButton.setOnClickListener { onAboutPressed() }
            exitButton.setOnClickListener { onExitPressed() }
        }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onOpenBoxPressed() {
        val intent = Intent(this, BoxSelectionActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        startResultLauncher.launch(intent)
    }

    private fun onOptionsPressed() {
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        startResultLauncher.launch(intent)
    }

    private fun onAboutPressed() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun onExitPressed() {
        finish()
    }

    companion object {
        const val KEY_OPTIONS = "OPTIONS"
    }
}




