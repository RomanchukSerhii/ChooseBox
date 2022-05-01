package com.example.studykotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.studykotlin.databinding.ActivityOptionsBinding
import com.example.studykotlin.model.Options
import java.lang.IllegalArgumentException

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var adapter: ArrayAdapter<BoxCountItem>
    private lateinit var options: Options
    private lateinit var boxCountItem: List<BoxCountItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?:
                intent.getParcelableExtra(EXTRA_OPTIONS) ?:
                throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument")

        setupSpinner()
        setupCheckBox()
        updateUI()

        binding.cancelButton.setOnClickListener { onCancelPressed() }
        binding.confirmButton.setOnClickListener { onConfirmPressed() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun setupSpinner() {
        boxCountItem = (1..6).map { BoxCountItem(it, "$it boxes") }

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            boxCountItem
        )
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val count = boxCountItem[p2].count
                options = options.copy(boxCount = count)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun setupCheckBox() {
        binding.enableTimerCheckBox.setOnClickListener {
            options = options.copy(isTimerEnabled = binding.enableTimerCheckBox.isChecked)
        }
    }

    private fun updateUI() {
        binding.enableTimerCheckBox.isChecked = options.isTimerEnabled
        val currentIndex = boxCountItem.indexOfFirst { it.count == options.boxCount }
        binding.spinner.setSelection(currentIndex)
    }

    private fun onCancelPressed() {
        finish()
    }

    private fun onConfirmPressed() {
        val intent = Intent()
        intent.putExtra(EXTRA_OPTIONS, options)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        const val KEY_OPTIONS = "KEY_OPTIONS"
    }

    class BoxCountItem(
        val count: Int,
        private val optionsTitle: String
    ) {
        override fun toString(): String {
            return if (count == 1)
                "$count box"
            else
                optionsTitle
        }
    }
}