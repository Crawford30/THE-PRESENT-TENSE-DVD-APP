package com.dvds.helpers

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.dvds.data.network.Resource
import kotlinx.coroutines.launch
import java.util.*

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.logout() = lifecycleScope.launch {
//    if (activity is HomeActivity) {
//        (activity as HomeActivity).performLogout()
//    }
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
//            if (this is LoginFragment) {
//                requireView().snackbar("You've entered incorrect email or password")
//            } else {
//                logout()
//            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}



//MAKING PART OF A TEXT BOLD
fun makeSectionOfTextBold(text: String, textToBold: String): SpannableString {

    val spannableString = SpannableString(text)

    if (text.contains(textToBold)) {

        //for counting start/end indexes

        val testText = text.toLowerCase(Locale.ROOT)
        val testTextToBold = textToBold.toLowerCase(Locale.ROOT)

        //======= counting start and end index =====

        val startingIndex = testText.indexOf(testTextToBold)
        val endingIndex = startingIndex + testTextToBold.length


        spannableString.setSpan(StyleSpan(Typeface.BOLD), startingIndex, endingIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        return spannableString

    } else {

        return spannableString
    }
}






//MAKING PART OF A TEXT BOLDitalic
fun makeSectionOfTextBoldItalic(text: String, textToBold: String): SpannableString {

    val spannableString = SpannableString(text)

    if (text.contains(textToBold)) {

        //for counting start/end indexes

        val testText = text.toLowerCase(Locale.ROOT)
        val testTextToBold = textToBold.toLowerCase(Locale.ROOT)

        //======= counting start and end index =====

        val startingIndex = testText.indexOf(testTextToBold)
        val endingIndex = startingIndex + testTextToBold.length


        spannableString.setSpan(StyleSpan(Typeface.BOLD), startingIndex, endingIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        return spannableString

    } else {

        return spannableString
    }
}


fun Fragment.setActivityTitle(@StringRes id: Int) {
    (activity as AppCompatActivity?)?.supportActionBar?.title = getString(id)
}

fun Fragment.setActivityTitle(title: String) {
    (activity as AppCompatActivity?)?.supportActionBar?.title = title
}