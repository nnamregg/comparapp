package com.curso.android.app.practica.comparapp.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curso.android.app.practica.comparapp.model.StringComparison
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel: ViewModel() {
    private var _stringComparison = MutableLiveData<StringComparison>()
    val stringComparison: LiveData<StringComparison> get() = _stringComparison

    // Declarar variable para guardar mensaje (en caso de) de error
    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    fun compareStrings(str1: String, str2: String) {
        try {
            // Validar input de usuario -> ningún string vacío (pueden tener espacios)
            if (str1.isNotEmpty() && str2.isNotEmpty()) {
                // Ejecutar comparación, actualizar data class y limpiar mensaje de error
                val comparisonResult = str1 == str2
                updateStringComparison(str1, str2, comparisonResult)
                updateErrorMsg(null)
            } else {
                throw Exception("Complete ambos campos.")
            }
        } catch (e: Exception) {
            updateErrorMsg("Error: ${e.message}")
            Log.e(TAG, "compareStrings catch; e -> $e")
        }
    }

    private fun updateStringComparison(str1: String, str2: String, comparisonResult: Boolean) {
        viewModelScope.launch {
            _stringComparison.value = StringComparison(str1, str2, comparisonResult)
        }
    }

    private fun updateErrorMsg(msg: String?) {
        viewModelScope.launch {
            _errorMsg.value = msg
        }
    }

}

