package com.example.investmenttracker.screens.investment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.investmenttracker.databinding.FragmentDeleteInvestmentBinding
import com.example.investmenttracker.models.Investment
import androidx.navigation.fragment.findNavController
import com.example.investmenttracker.R

class DeleteInvestmentFragment : Fragment(R.layout.fragment_delete_investment)  {

    private var _binding: FragmentDeleteInvestmentBinding? = null
    private val binding get() = _binding!!

    // SharedPreferences to the ViewModelFactory
    private val viewModel: InvestmentViewModel by activityViewModels {
        InvestmentViewModelFactory(requireActivity().getSharedPreferences("InvestmentPrefs", Context.MODE_PRIVATE))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteInvestmentBinding.inflate(inflater, container, false)
        val investment = requireArguments().getParcelable<Investment>("investment")
        binding.investment = investment
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDeleteInvestment.setOnClickListener {
            binding.investment?.let { investment ->
                viewModel.deleteInvestment(investment)
                // Navigate back or show a message that the investment was deleted
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

