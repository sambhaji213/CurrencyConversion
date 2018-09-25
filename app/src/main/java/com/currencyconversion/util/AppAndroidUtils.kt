package com.currencyconversion.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.currencyconversion.R
import com.currencyconversion.base.MyApplicationClass
import java.text.SimpleDateFormat
import java.util.*

/*
 * Created by Sambhaji Karad on 25/09/2018
*/

class AppAndroidUtils {
    companion object {

        fun hideKeyboard(activity: Activity) {

            val view = activity.currentFocus
            if (view != null) {
                val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        fun startFwdAnimation(activity: Activity) {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        private fun isNetWorkAvailable(showMessage: Boolean): Boolean {
            val connMgr = MyApplicationClass.appContext!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connMgr.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                return true
            } else if (showMessage) {
                Toast.makeText(MyApplicationClass.appContext,
                        MyApplicationClass.appContext!!.getString(R.string.hint_networkError),
                        Toast.LENGTH_LONG).show()
            }
            return false
        }

        fun isNetWorkAvailable(): Boolean {
            return isNetWorkAvailable(true)
        }


        fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)
        }
    }
}
