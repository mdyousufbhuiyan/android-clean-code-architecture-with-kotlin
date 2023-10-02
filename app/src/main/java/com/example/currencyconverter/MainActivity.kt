package com.example.currencyconverter

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.model.ItemsModel
import com.example.currencyconverter.adapter.CurrencyAdapter
import com.example.currencyconverter.data.PreferenceHelper
import com.example.currencyconverter.data.PreferenceHelper.currencies
import com.example.currencyconverter.data.PreferenceHelper.currenciesWithExchangeRate
import com.example.currencyconverter.utils.*
import com.example.currencyconverter.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var _binding: ActivityMainBinding
    private var imageUrl: String? = null
    private lateinit var spinnerAdapter: ArrayAdapter<Any>
    var currencyResponse: LinkedTreeMap<Any, Any> = LinkedTreeMap<Any, Any>()
    var currencyWithExchangeRateResponse: LinkedTreeMap<Any, Any> = LinkedTreeMap<Any, Any>()
    var selectedCurrency = "AED"
    lateinit var preferences: SharedPreferences
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        preferences = PreferenceHelper.customPreference(this)
        _binding.container.setOnRefreshListener {
            _binding.container.isRefreshing = true
            fetchResponse()
        }
        fetchData()

        _binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convert()
            }
        })
    }

    private fun convert() {
        //   _binding.etAmount.hideKeyboard()
        if (InputChecker.isValidInput(_binding.etAmount.text.toString())) {
            var dataList = Converter.convertCurrencies(
                selectedCurrency,
                _binding.etAmount.text.toString(),
                currencyWithExchangeRateResponse,
                currencyResponse
            )
            buildRecyclerView(dataList)
        }
    }

    private fun fetchResponse() {
        mainViewModel.fetchCurrencyResponse()
        mainViewModel.fetchCurrenciesWithExchangeRate()
        _binding.pbDog.visibility = View.VISIBLE
    }


    private fun fetchData() {
        fetchResponse()
        mainViewModel.currencyResponse.observe(this) { response ->
            _binding.container.isRefreshing = false
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        currencyResponse = response.data as LinkedTreeMap<Any, Any>
                        buildSpinner()
                    }
                    var response = gson.toJson(currencyResponse)
                    preferences.currencies = response
                    //    _binding.pbDog.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    _binding.pbDog.visibility = View.GONE
//                    Toast.makeText(
//                        this,
//                        response.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
                is NetworkResult.Loading -> {
                    _binding.pbDog.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        mainViewModel.currencyWithExchangeRateResponse.observe(this) { response ->
            _binding.container.isRefreshing = false
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        val t: LinkedTreeMap<Any, Any> = response.data as LinkedTreeMap<Any, Any>
                        val obj: LinkedTreeMap<Any, Any> =
                            t.get(key = "rates") as LinkedTreeMap<Any, Any>
                        currencyWithExchangeRateResponse = obj
                        var response = gson.toJson(t)
                        preferences.currenciesWithExchangeRate = response
                    }
                    _binding.pbDog.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    _binding.pbDog.visibility = View.GONE
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    _binding.pbDog.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    private fun buildSpinner() {
        var keysList = ArrayList<Any>();
        keysList.addAll(currencyResponse.keys.toList() as List<String>)
        // Create an ArrayAdapter using a simple spinner layout and languages array
        spinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, keysList)
        // Set layout to use when the list of choices appear
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        _binding.spinner!!.setAdapter(spinnerAdapter)
        _binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCurrency = keysList.get(position).toString()
                convert()
            }

        }
    }

    private fun buildRecyclerView(list: ArrayList<ItemsModel>) {
        _binding.recyclerView.layoutManager = LinearLayoutManager(this)
        // This will pass the ArrayList to our Adapter
        val adapter = CurrencyAdapter(list)
        // Setting the Adapter with the recyclerview
        _binding.recyclerView.adapter = adapter
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onBackPressed() {
        showExitConfirmationAlert()
    }


    private fun showExitConfirmationAlert() {
        val mBuilder = AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes", null)
            .setNegativeButton("No", null)
            .show()

        // Function for the positive button
        // is programmed to exit the application
        val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
        mPositiveButton.setOnClickListener {
            exitProcess(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
