package com.example.bankingapp.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.bankingapp.R
import com.example.bankingapp.ui.model.User
import kotlinx.android.synthetic.main.activity_show_profile.*
import kotlinx.android.synthetic.main.enter_amount_layout.*
import kotlinx.android.synthetic.main.enter_amount_layout.view.*

class ShowProfileActivity : AppCompatActivity() {
    lateinit var amount:String
    lateinit var mUser: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)
        mUser=intent.getSerializableExtra("user") as User
        showProfile(mUser)

        transferMoneyButton.setOnClickListener {

            val amountDialog= AlertDialog.Builder(this)
            val dialogLayout=layoutInflater.inflate(R.layout.enter_amount_layout,null)
            amountDialog.setMessage("Enter an amount")
                .setView(dialogLayout)
                .setPositiveButton("Yes"){dialoginterface,i->
                    val editText:EditText=dialogLayout.findViewById(R.id.et_enterAmount)
                    amount=editText.text.toString().trim()
                    if (amount.isEmpty()){
                        Toast.makeText(this,"Enter an amount",Toast.LENGTH_SHORT).show()
                        editText.error = "Enter a amount"

                    }else{

                        val intent= Intent(this,ShowUser::class.java)
                        intent.putExtra("mUser",mUser)
                        intent.putExtra("amount",amount)
                        startActivity(intent)
                    }

                }
                .setNegativeButton("No"){dialoginterface,i->
                    Log.d("main","negative block")
                }
                .create()
                .show()
        }
    }

    private fun showProfile(user: User) {
        nameText.text=user.name
        accountNoText.text=user.accountNo.toString()
        balanceText.text=user.balance.toString()
        ifscCodeText.text=user.ifscCode
        emailText.text=user.email
        addressText.text=user.address
        phoneNumberText.text=user.phoneNo

    }
}