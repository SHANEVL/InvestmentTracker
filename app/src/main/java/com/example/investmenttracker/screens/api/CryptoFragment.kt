    package com.example.investmenttracker.screens.api

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.View
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.investmenttracker.R
    import com.example.investmenttracker.databinding.FragmentCryptoBinding
    import com.example.investmenttracker.screens.api.adapter.CryptoAdapter
    import okhttp3.*
    import java.io.IOException
    import com.google.gson.Gson
    import com.google.gson.annotations.SerializedName

    class CryptoFragment : Fragment(R.layout.fragment_crypto) {

        private var _binding: FragmentCryptoBinding? = null
        private val binding get() = _binding!!
        private val client = OkHttpClient()
        private lateinit var cryptoAdapter: CryptoAdapter

        // no need for oncreateview because there is no shared data with the investment screens
        // ofcourse in a real world application, their would be because you would
        // be able to select a token and maybe make an investment object of them

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            _binding = FragmentCryptoBinding.bind(view)

            setupRecyclerView()

            binding.buttonSearch.setOnClickListener {
                val query = binding.editTextSearchQuery.text.toString()
                if (query.isNotEmpty()) {
                    searchCoins(query)
                }
            }
        }

        private fun setupRecyclerView() {
            cryptoAdapter = CryptoAdapter(emptyList()) // Initialize the adapter
            binding.recyclerViewSearchResults.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = cryptoAdapter
            }
        }

        private fun searchCoins(query: String) {
            val request = Request.Builder()
                .url("https://api.coingecko.com/api/v3/search?query=$query")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle the error, update UI as necessary
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use { res ->
                        if (res.isSuccessful) {
                            // Parse the JSON response to extract the coin names
                            val responseBody = res.body?.string()
                            val coinResponse = Gson().fromJson(responseBody, CoinGeckoResponse::class.java)
                            val coinNames = coinResponse.coins.map { it.name }

                            // Update the adapter with the new list of coin names
                            activity?.runOnUiThread {
                                cryptoAdapter.updateData(coinNames)
                            }
                        } else {
                            // Handle the error, update UI as necessary
                        }
                    }
                }
            })
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        // Define the data model for the Gson parser based on the CoinGecko API response
        data class CoinGeckoResponse(val coins: List<Coin>)
        data class Coin(
            val id: String,
            val name: String,
            @SerializedName("api_symbol") val apiSymbol: String,
            val symbol: String,
            @SerializedName("market_cap_rank") val marketCapRank: Int,
            val thumb: String,
            val large: String
        )
    }
