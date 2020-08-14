package com.example.earsna.model

data class User(
    var first_name: String,
    var last_name : String,
    var email : String,
    var password : String,
    var phone_number : String,
    var role : Int? = null,
    var country : String? = null,
    var city : String? = null
)