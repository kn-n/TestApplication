package com.example.testapplication

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun makeDate(str: String) : String{
    var str1 = str.replace("Z","")
    val date1 = str1.split("T")
    val date = date1[0].split("-")
    val time = date1[1]
    return " ${date[2]}.${date[1]}.${date[0]} at $time"
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun makeClient(context: Context) : GoogleSignInClient{
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("1046064538612-oj7n71hpc0kdrnetuj6rv4j4oslsh5oi.apps.googleusercontent.com")
        .requestId()
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

