package com.example.composetest

class Person (
    var firstName: String,
    var lastName: String,
    var rating: Float,
    var imageId: Int,
    var cars: MutableList<Car> = mutableListOf<Car>()
){
    fun fullName(): String = "$firstName $lastName"
}