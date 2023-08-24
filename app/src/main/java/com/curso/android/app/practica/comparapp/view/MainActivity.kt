package com.curso.android.app.practica.comparapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.curso.android.app.practica.comparapp.R
import com.curso.android.app.practica.comparapp.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.stringComparison.observe(this) {
            Log.i(TAG, "Actualizado valor de stringComparison -> $it")
            setResultTxt(it.comparisonResult)
        }

        mainViewModel.errorMsg.observe(this) {
            Log.i(TAG, "Actualizado valor de errorMsg -> $it")
            if (!it.isNullOrEmpty()) showError(it)
        }

        binding.compareBtn.setOnClickListener {
            // Esconder el teclado antes de ejecutar la comparación
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

            // Obtener valores desde la UI y ejecutar comparación
            val str1: String = binding.str1.text.toString()
            val str2: String = binding.str2.text.toString()
            mainViewModel.compareStrings(str1, str2)
        }
    }

    private fun showError(msg: String) {
        // Setear texto y color en caso de error
        val color = getColor(R.color.error)
        setTextView(msg, color)
    }

    private fun setResultTxt(comparisonResult: Boolean) {
        // Setear texto y color en base al resultado
        val txt: String
        val color: Int

        when(comparisonResult) {
            true -> {
                txt = getString(R.string.result_ok)
                color = getColor(R.color.result_ok)
            }
            false -> {
                txt = getString(R.string.result_fail)
                color = getColor(R.color.result_fail)
            }
        }

        setTextView(txt, color)
    }

    private fun setTextView(txt: String, color: Int) {
        binding.resultTxt.text = txt
        binding.resultTxt.setTextColor(color)
    }

}