package com.currencyconversion.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.currencyconversion.R
import com.currencyconversion.rest.ApiClient
import com.currencyconversion.rest.ApiInterface
import com.currencyconversion.util.AppAndroidUtils
import com.currencyconversion.util.AppConstants
import com.currencyconversion.util.AppWaitDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * Created by Sambhaji Karad on 25/09/2018
*/

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name
    private var mWaitDialog: AppWaitDialog? = null

    private var mAdapterSelected: ArrayAdapter<String>? = null
    private var mAdapterConverted: ArrayAdapter<String>? = null
    private var selectedCountry = ""
    private var convertedCountry = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWaitDialog = AppWaitDialog(this@MainActivity)

        getCurrencyData()
        setUpDataWithUI()
    }

    /*Set the country list to spinner*/
    private fun getCurrencyData() {
        mAdapterSelected = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1,
                AppConstants.countryValue)
        spinnerSelectedCurrency.adapter = mAdapterSelected!!

        mAdapterConverted = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1,
                AppConstants.countryValue)
        spinnerConvertedCurrency.adapter = mAdapterConverted!!
    }

    /*set up the data and listener to view*/
    @SuppressLint("SetTextI18n")
    private fun setUpDataWithUI() {
        textViewTodayDate.text = "Exchange Date - "+AppAndroidUtils.getCurrentDateTime()
        spinnerSelectedCurrency.setSelection(32)
        spinnerConvertedCurrency.setSelection(15)
        editTextSelectedCurrency.setSelection(editTextSelectedCurrency.length())

        spinnerSelectedCurrency?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCountry = spinnerSelectedCurrency.selectedItem.toString()
                if (!selectedCountry.isEmpty() && !convertedCountry.isEmpty()){
                    getSelectedCountryConversion(selectedCountry, convertedCountry, true)
                }
            }
        }

        spinnerConvertedCurrency?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertedCountry = spinnerConvertedCurrency.selectedItem.toString()
                if (!selectedCountry.isEmpty() && !convertedCountry.isEmpty()) {
                    getSelectedCountryConversion(selectedCountry, convertedCountry, true)
                }
            }
        }

        editTextSelectedCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()){
                    if (s.toString() == AppConstants.ZERO){
                        editTextSelectedCurrency.setText("1")
                        Toast.makeText(this@MainActivity, "Source currency should be not zero", Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (!selectedCountry.isEmpty() && !convertedCountry.isEmpty()) {
                        getSelectedCountryConversion(selectedCountry, convertedCountry, false)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    /*get selected country conversion from api and show result*/
    private fun getSelectedCountryConversion(selectedCountry: String, convertedCountry: String, isFromEditText: Boolean) {
        if (AppAndroidUtils.isNetWorkAvailable()) {
            if (isFromEditText) {
                mWaitDialog!!.show()
            }
            val apiService = ApiClient.getClientString()!!.create(ApiInterface::class.java)
            val call = apiService.getCurrencyConvert(selectedCountry, convertedCountry)

            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    mWaitDialog!!.dismiss()
                    if (response.isSuccessful) {
                        val inputValue = editTextSelectedCurrency.text.toString()
                        val responseJson = response.body()
                        val jsonObject = JSONObject(responseJson)
                        val rateObject = jsonObject.getJSONObject(AppConstants.RATES)
                        val convertedValue = rateObject.optString(convertedCountry)

                        if (!inputValue.isEmpty()) {
                            val finalResult = (inputValue.toFloat() * convertedValue.toFloat())
                            editTextConvertedCurrency.setText(finalResult.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e(TAG, t.toString())
                    mWaitDialog!!.dismiss()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_refresh -> {
                getSelectedCountryConversion(selectedCountry, convertedCountry, true)
                return true
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppAndroidUtils.hideKeyboard(this@MainActivity)
        this.finish()
    }
}
