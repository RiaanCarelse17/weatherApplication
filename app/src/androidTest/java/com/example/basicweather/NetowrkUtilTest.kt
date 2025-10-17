package com.example.basicweather

import org.junit.Test
import org.junit.Assert.*
import java.net.URL

class NetworkUtilTest {

    @Test
    fun `buildURLForWeather returns valid URL with correct parameters`() {
        val url: URL? = NetworkUtil.buildURLForWeather()
        assertNotNull("URL should not be null", url)

        val urlString = url.toString()

        // Check base path
        assertTrue("URL should contain base path", urlString.contains("https://dataservice.accuweather.com/forecasts/v1/daily/5day/306633"))

        // Check API key presence (not value)
        assertTrue("URL should contain API key parameter", urlString.contains("apikey="))

        // Check metric flag
        assertTrue("URL should contain metric=true", urlString.contains("metric=true"))
    }
}
