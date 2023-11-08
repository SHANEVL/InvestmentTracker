// ViewInvestmentsFragment.kt

package com.example.investmenttracker.screens.investment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investmenttracker.databinding.FragmentViewInvestmentsBinding
import com.example.investmenttracker.models.Investment
import com.example.investmenttracker.screens.investment.adapter.InvestmentAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.investmenttracker.R

class ViewInvestmentsFragment : Fragment(R.layout.fragment_view_investments)  {

    private var _binding: FragmentViewInvestmentsBinding? = null
    private val binding get() = _binding!!
    // provide the SharedPreferences to the ViewModelFactory
    private val viewModel: InvestmentViewModel by activityViewModels {
        InvestmentViewModelFactory(requireActivity().getSharedPreferences("InvestmentPrefs", Context.MODE_PRIVATE))
    }

    private lateinit var investmentAdapter: InvestmentAdapter
    private var selectedInvestment: Investment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentViewInvestmentsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView and adapter first
        setupRecyclerView()

        viewModel.investments.observe(viewLifecycleOwner, Observer { investments ->
            investmentAdapter.updateInvestments(investments ?: emptyList())
        })

        binding.btnCreateInvestment.setOnClickListener {
            navigateToCreateInvestment()
        }

        binding.btnDeleteInvestment.setOnClickListener {
            selectedInvestment?.let { investment ->
                navigateToDeleteInvestment(investment)
            } ?: showToast("Please select an investment to delete.")
        }

        binding.btnResearchInvestment.setOnClickListener {
            navigateToCrypto()
        }
    }


    private fun setupRecyclerView() {
        investmentAdapter = InvestmentAdapter(mutableListOf()) { investment ->
            selectedInvestment = if (selectedInvestment == investment) null else investment
            investmentAdapter.notifyDataSetChanged() // Refresh the RecyclerView to show the selected item
        }

        binding.recyclerViewInvestments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = investmentAdapter
        }
    }

    private fun navigateToCreateInvestment() {
        val action = ViewInvestmentsFragmentDirections.actionViewInvestmentsFragmentToCreateInvestmentFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDeleteInvestment(investment: Investment) {
        val action = ViewInvestmentsFragmentDirections.actionViewInvestmentsFragmentToDeleteInvestmentFragment(investment)
        findNavController().navigate(action)
    }

    private fun navigateToCrypto() {
        val action = ViewInvestmentsFragmentDirections.actionViewInvestmentsFragmentToCryptoFragment()
        findNavController().navigate(action)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding
    }
}
