package com.example.bankingapp.ui.model

import java.io.Serializable

data class User(val id:Int,val name:String,val accountNo:String,val ifscCode:String,val email:String,val balance:Double,val address:String,val phoneNo:String):Serializable