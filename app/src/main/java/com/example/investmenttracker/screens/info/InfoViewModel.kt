package com.example.investmenttracker.screens.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {
    val vivesId = MutableLiveData<String>().apply { value = "r0703874" }
    val emailAddress = MutableLiveData<String>().apply { value = "shane.vanloo@vives.be" }
}

