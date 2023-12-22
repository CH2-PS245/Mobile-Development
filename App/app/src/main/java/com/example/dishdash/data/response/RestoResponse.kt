package com.example.dishdash.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RestoResponse(

	@field:SerializedName("RestoResponse")
	val restoResponse: List<RestoResponseItem?>? = null
) : Parcelable

@Parcelize
data class RestoResponseItem(
	@field:SerializedName("photoUrlFood")
	val photoUrlFood: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("latlong")
	val latlong: List<String?>? = null,

	@field:SerializedName("name_food")
	val nameFood: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("calorie")
	val calorie: String? = null,

	@field:SerializedName("rating")
	val rating: Float? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("photoUrlResto")
	val photoUrlResto: String? = null
) : Parcelable
