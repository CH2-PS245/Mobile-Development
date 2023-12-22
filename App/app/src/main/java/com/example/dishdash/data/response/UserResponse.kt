package com.example.dishdash.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserResponse(
	val data: DataUser? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataUser(
	val createdAt: String? = null,
	val profile: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null,
	val updatedAt: String? = null
) : Parcelable
