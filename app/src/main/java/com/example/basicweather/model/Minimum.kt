package com.example.basicweather.model

import com.google.gson.annotations.SerializedName


data class Minimum (

  @SerializedName("Value"    ) var Value    : Float?    = null,
  @SerializedName("Unit"     ) var Unit     : String? = null,
  @SerializedName("UnitType" ) var UnitType : Int?    = null

)