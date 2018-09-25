package com.currencyconversion.util

import android.app.ProgressDialog
import android.content.Context

/*
 * Created by Sambhaji Karad on 25/09/2018
*/
/**
 * The Class ProgressDialog.
 */
class AppWaitDialog : ProgressDialog {

    /**
     * Instantiates a new oE dialog.
     *
     * @param context
     * the context
     */
    constructor(context: Context) : super(context) {
        this.setMessage("Please wait...")
        this.setCancelable(false)
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new oE dialog.
     *
     * @param context
     * the context
     * @param isCancelable
     * the is cancelable
     * @param message
     * the message
     */
    constructor(context: Context, isCancelable: Boolean, message: String) : super(context) {
        this.setTitle("Please wait...")
        this.setCancelable(isCancelable)
        this.setMessage(message)
        this.setCancelable(false)
    }
}
