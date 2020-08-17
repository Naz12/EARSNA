package com.example.earsna.model

data class User(
    var first_name: String? = null,
    var last_name : String? = null,
    var email : String? = null,
    var password : String? = null,
    var phone_number : String? = null,
    var role : Int? = null,
    var country : String? = null,
    var city : String? = null
){}