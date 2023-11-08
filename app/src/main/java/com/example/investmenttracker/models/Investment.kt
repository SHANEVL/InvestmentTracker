package com.example.investmenttracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Investment(
    val id: Int,
    val investmentName: String,
    val investmentAmount: String,
    val investmentType: String,
    val investmentDate: String,
    val buyPrice: String
) : Parcelable {
    val investmentValue: String
        get() {
            val amount = investmentAmount.toDoubleOrNull() ?: return "Invalid amount"
            val price = buyPrice.toDoubleOrNull() ?: return "Invalid price"
            return (amount * price).toString()
        }
}


