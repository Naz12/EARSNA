package com.example.earsna.model


data class Service(
    var id: String? = null,
    val name: String? = null,
    var price: Double? = null,
    var categoryId : String? = null,  // like wedding , club ...
    var createorId : String? = null,  // creator user info ref
    var isActive : Boolean? = false,     // whather the service is running or stop by an reason
    var address : String? = null,
    var type : List<String>? = null,     // info like club for party ? = null, promo ? = null, and other activity
    var images : List<String>? = null,
    var service_details : Map<String, String>? = null,    // like size : 400(size is key and 400 is value)
    var city : String? = null,
    var country : String


    
)