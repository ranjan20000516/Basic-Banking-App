package com.example.bankingapp.ui.model

data class Transaction(val transactionId: Int,val amount:Double,val toName:String,val fromName:String,val status:String,val date:String)