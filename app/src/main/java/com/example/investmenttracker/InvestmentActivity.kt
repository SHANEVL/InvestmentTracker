package com.example.investmenttracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.investmenttracker.databinding.ActivityInvestmentBinding
import com.example.investmenttracker.screens.investment.InvestmentViewModel
import com.example.investmenttracker.screens.investment.InvestmentViewModelFactory

class InvestmentActivity : BaseActivity() {
    private lateinit var binding: ActivityInvestmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_investment)
    }
}
