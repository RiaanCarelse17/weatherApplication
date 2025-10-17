package com.example.basicweather

import android.content.Intent
import android.net.*
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.basicweather.NetworkUtil.buildURLForWeather
import com.example.basicweather.databinding.ActivityMainBinding
import com.example.basicweather.model.DailyForecasts
import com.example.basicweather.model.ExampleJson2KtKotlin
import com.google.gson.Gson
import org.json.*
import java.net.HttpURLConnection
import kotlin.concurrent.thread

private var fiveDaylList = mutableListOf<Forecast>()
private val LOGGING_TAG = "weatherDATA"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weatherUrl = buildURLForWeather()
       // val tvWeather = findViewById<TextView>(R.id.tvWeather)
       // val logo = findViewById<ImageView>(R.id.accuLogo)

        fun consumeJson(weatherJSON: String?) {
            if (fiveDaylList.isNotEmpty()) {
                fiveDaylList.clear()
            }

            if (weatherJSON != null) {
                try {
                    val gson = Gson()
                    val weatherData = gson.fromJson(weatherJSON, ExampleJson2KtKotlin::class.java)

                    for (forecast in weatherData.DailyForecasts) {
                        val forecastObject = Forecast()

                        // Trim date to just YYYY-MM-DD if possible
                        val rawDate = forecast.Date ?: "N/A"
                        val formattedDate = if (rawDate.length >= 10) rawDate.substring(0, 10) else rawDate

                        val minTemp = forecast.Temperature?.Minimum?.Value?.toString() ?: "N/A"
                        val maxTemp = forecast.Temperature?.Maximum?.Value?.toString() ?: "N/A"

                        Log.i(LOGGING_TAG, "consumeJson: Date $formattedDate")
                        Log.i(LOGGING_TAG, "consumeJson: minTemp $minTemp")
                        Log.i(LOGGING_TAG, "consumeJson: maxTemp $maxTemp")

                        forecastObject.date = formattedDate
                        forecastObject.minimumTemperature = minTemp
                        forecastObject.maximumTemperature = maxTemp

                        fiveDaylList.add(forecastObject)

                       // tvWeather.append("Date: $formattedDate Min: $minTemp Max: $maxTemp\n")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
              //  tvWeather.text = "Failed to load weather data."
            }
        }



        thread {
            val result = try {
                val connection = weatherUrl?.openConnection() as? HttpURLConnection
                connection?.requestMethod = "GET"
                connection?.connect()

                connection?.inputStream?.bufferedReader()?.use { it.readText() }
            } catch (e: Exception) {
                Log.e("WeatherApp", "Network error", e)
                null
            }

            runOnUiThread {
                consumeJson(result)
            }
        }

       /* logo.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.accuweather.com/")
            )
            startActivity(intent)
        }*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

class Forecast {
    var date: String = ""
    var minimumTemperature: String = ""
    var maximumTemperature: String = ""
}
