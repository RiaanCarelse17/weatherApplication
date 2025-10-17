package com.example.basicweather

import android.net.Uri
import android.util.Log
import java.net.MalformedURLException
import java.net.URL
import com.example.basicweather.BuildConfig


object NetworkUtil {
    private const val WEATHER_BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/306633"
    private const val PARAM_API_KEY = "apikey"
    private const val PARAM_METRIC = "metric"
    private const val METRIC_VALUE = "true"
    private const val LOGGING_TAG = "URLWECREATED"
    val apiKey = BuildConfig.ACCUWEATHER_API_KEY

    fun buildURLForWeather(): URL? {
        val buildUri: Uri = Uri.parse(WEATHER_BASE_URL).buildUpon()
            .appendQueryParameter(PARAM_API_KEY, BuildConfig.ACCUWEATHER_API_KEY)
            .appendQueryParameter(PARAM_METRIC, METRIC_VALUE)
            .build()

        var url: URL? = null
        try {
            url = URL(buildUri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        Log.i(LOGGING_TAG, "buildURLForWeather: $url")
        return url
    }
}
