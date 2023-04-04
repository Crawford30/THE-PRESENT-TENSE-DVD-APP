package com.dvds.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SavedPreference {

    const val EMAIL= "email"
    const val USERNAME="username"
    const val FULLNAME = "fullname"
    const val ACCESSCODE = "accesscode"
    const val COUNTRYCODE= "country"
    const val COMBINEDVERCODE= "combinedcode"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }


    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }



    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")


    fun setUserFullName(context: Context, fullname:String){
        editor(
            context,
            USERNAME,
            fullname
        )
    }

    fun getFulllName(context: Context) = getSharedPreference(
        context
    )?.getString(FULLNAME,"")



    fun setCombinedCode(context: Context, combinedcode:String){
        editor(
            context,
            COMBINEDVERCODE,
            combinedcode
        )
    }

    fun getCombinedCode(context: Context) = getSharedPreference(
        context
    )?.getString(COMBINEDVERCODE,"")



    fun setAccessCode(context: Context, accesscode:String){
        editor(
            context,
            ACCESSCODE,
            accesscode
        )
    }

    fun getAccessCode(context: Context) = getSharedPreference(
        context
    )?.getString(ACCESSCODE,"")


    fun setCountryCode(context: Context, country:String){
        editor(
            context,
            USERNAME,
            country
        )
    }


    fun getCountryCode(context: Context) = getSharedPreference(
        context
    )?.getString(COUNTRYCODE,"")

}

