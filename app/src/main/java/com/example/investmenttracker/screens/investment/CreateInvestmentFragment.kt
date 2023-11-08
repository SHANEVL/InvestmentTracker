package com.example.investmenttracker.screens.investment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.investmenttracker.databinding.FragmentCreateInvestmentBinding
import com.example.investmenttracker.models.Investment
import androidx.navigation.fragment.findNavController
import com.example.investmenttracker.R
import java.text.SimpleDateFormat
import java.util.*

class CreateInvestmentFragment : Fragment(R.layout.fragment_create_investment) {

    private var _binding: FragmentCreateInvestmentBinding? = null
    private val binding get() = _binding!!

    // SharedPreferences to the ViewModelFactory
    private val viewModel: InvestmentViewModel by activityViewModels {
        InvestmentViewModelFactory(requireActivity().getSharedPreferences("InvestmentPrefs", Context.MODE_PRIVATE))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateInvestmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCreateInvestment.setOnClickListener {
            createInvestment()
        }
    }

    private fun createInvestment() {
        val name = binding.editInvestmentName.text.toString()
        val amount = binding.editInvestmentAmount.text.toString()
        val type = binding.editInvestmentType.text.toString()
        val buyPrice = binding.editBuyPrice.text.toString()

        // Automatically set the date to the current system date
        val currentDate = getCurrentDate()

        val newInvestment = Investment(
            id = viewModel.generateNewId(),
            investmentName = name,
            investmentAmount = amount,
            investmentType = type,
            investmentDate = currentDate,
            buyPrice = buyPrice
        )
        Log.d("CreateInvestment", "Investment to add: $newInvestment")
        viewModel.addInvestment(newInvestment)
        findNavController().popBackStack()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
