package com.example.dishdash.model

import com.example.dishdash.R

object DummyData {

    fun generateAllRestaurants(): List<RestaurantModel> {
        return listOf(
            RestaurantModel("1", "Restoran 1", "Deskripsi Restoran 1", 4.5f, R.drawable.icon_app),
            RestaurantModel("2", "Restoran 2", "Deskripsi Restoran 2", 4.0f, R.drawable.icon_app),
            RestaurantModel("3", "Restoran 3", "Deskripsi Restoran 3", 3.0f, R.drawable.icon_app),
            RestaurantModel("4", "Restoran 4", "Deskripsi Restoran 4", 2.0f, R.drawable.icon_app),
            RestaurantModel("5", "Restoran 5", "Deskripsi Restoran 5", 1.0f, R.drawable.icon_app),
        )
    }

    fun generateNearbyRestaurants(): List<RestaurantModel> {
        return listOf(
            RestaurantModel("6", "Restoran Terdekat 1", "Deskripsi Terdekat 1", 4.8f, R.drawable.welcome_image),
            RestaurantModel("7", "Restoran Terdekat 2", "Deskripsi Terdekat 2", 4.2f, R.drawable.welcome_image),
            RestaurantModel("8", "Restoran Terdekat 3", "Deskripsi Terdekat 3", 4.0f, R.drawable.welcome_image),
            RestaurantModel("9", "Restoran Terdekat 4", "Deskripsi Terdekat 4", 3.2f, R.drawable.welcome_image),
            RestaurantModel("10", "Restoran Terdekat 5", "Deskripsi Terdekat 5", 2.2f, R.drawable.welcome_image),

            )
    }
}