package com.example.earsna.model

import java.util.*

class Booking(
    var id : String? = null,
    var bookingTime : String? = null, // like morning ...
    var dateFrom : String? = null,   // date object converted to string b/c firebase doesn't support date object
    var dateTo : String? = null, // date object converted to string b/c firebase doesn't support date object
    var bookingDate : String? = null,   // date which booking is made   // date object converted to string b/c firebase doesn't support date object
    var status : Int? = null,     // like completed = 0 , deposited  = 1, temporary = 2,
    var paymentType : Int? = null,  // like completed = 0 , deposited  = 1, temporary = 2
    var depositedAmount : Double? = null, // this field is populated when the payment type is deposit
    var serivceId : String? = null,    // Service id for booking
    var buyerId : String      // user id who want to book the above Service
)