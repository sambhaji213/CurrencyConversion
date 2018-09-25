package com.currencyconversion.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.currencyconversion.R
import com.currencyconversion.util.AppAndroidUtils

/**
 * Created by Sambhaji Karad on 25/09/18.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            openNextActivity()
        }, 1000)
    }

    private fun openNextActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
        AppAndroidUtils.startFwdAnimation(this@SplashActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //Do nothing
    }
}
