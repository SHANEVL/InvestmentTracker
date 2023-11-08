package com.example.investmenttracker.screens.investment

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.investmenttracker.models.Investment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InvestmentViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val gson = Gson()
    private val _investments = MutableLiveData<List<Investment>>()

    val investments: LiveData<List<Investment>> get() = _investments

    // LiveData for the new investment details
    val newInvestmentName = MutableLiveData<String>()
    val newInvestmentAmount = MutableLiveData<String>() // Now using String
    val newInvestmentType = MutableLiveData<String>()
    val newInvestmentDate = MutableLiveData<String>()
    val newInvestmentBuyPrice = MutableLiveData<String>() // Now using String

    init {
        loadInvestments()
    }

    private fun loadInvestments() {
        // Fetch and deserialize the investments JSON from SharedPreferences
        sharedPreferences.getString("investments", null)?.let { json ->
            val type = object : TypeToken<List<Investment>>() {}.type
            val investmentsList: List<Investment> = gson.fromJson(json, type)
            _investments.value = investmentsList
        } ?: run {
            // If there is nothing in SharedPreferences, use default list
            _investments.value = mutableListOf(
                Investment(1, "Investment 1", "1000.0", "Type A", "2023-01-01", "100.0"),
                Investment(2, "Investment 2", "2000.0", "Type B", "2023-01-02", "200.0")
            )
        }
    }

    private fun saveInvestments() {
        // Serialize the current list of investments to JSON and save in SharedPreferences
        val json = gson.toJson(_investments.value)
        sharedPreferences.edit().putString("investments", json).apply()
    }

    fun addInvestment(investment: Investment) {
        val updatedList = _investments.value?.toMutableList() ?: mutableListOf()
        updatedList.add(investment)
        _investments.value = updatedList
        saveInvestments() // Save every time the list is updated
    }

    fun addNewInvestment() {
        // Ensure that the input is valid or use default values
        val investment = Investment(
            id = generateNewId(),
            investmentName = newInvestmentName.value.orEmpty(),
            investmentAmount = newInvestmentAmount.value.orEmpty(),
            investmentType = newInvestmentType.value.orEmpty(),
            investmentDate = newInvestmentDate.value.orEmpty(),
            buyPrice = newInvestmentBuyPrice.value.orEmpty()
        )
        addInvestment(investment)
    }

    fun generateNewId(): Int {
        return (_investments.value?.maxByOrNull { it.id }?.id ?: 0) + 1
    }

    fun deleteInvestment(investment: Investment) {
        _investments.value = _investments.value?.filter { it.id != investment.id }
        saveInvestments() // Save the updated list after deletion.
    }

    // Other ViewModel functions...
}
