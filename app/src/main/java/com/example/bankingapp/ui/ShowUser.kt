package com.example.bankingapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.R
import com.example.bankingapp.ui.adapter.UserAdapter
import com.example.bankingapp.ui.adapter.onItemClickedListerner
import com.example.bankingapp.ui.database.DatabaseHandler
import com.example.bankingapp.ui.model.User
import kotlinx.android.synthetic.main.activity_show_user.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class ShowUser : AppCompatActivity(), onItemClickedListerner {

    private lateinit var userAdapter: UserAdapter
    private lateinit var amount:String
    private lateinit var fromName:String
    private lateinit var status:String
    lateinit var mUser:User
    lateinit var aUser:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user)

       mUser=intent.getSerializableExtra("mUser") as User
        amount=intent.getStringExtra("amount").toString()
        fromName=mUser.name
        setRecycleView()

        getUserList(mUser.id)
    }

    private fun setRecycleView() {
        userAdapter= UserAdapter(this)
        recycleView.adapter=userAdapter
        recycleView.layoutManager=LinearLayoutManager(this)
    }

    private fun getUserList(id:Int){
        val db=DatabaseHandler(this)
        val userList=db.showUserToTransferMoney(id)
        userAdapter.differ.submitList(userList)
    }
    private fun showDialog(user:User){
        val userDialog=AlertDialog.Builder(this)
            .setMessage("Do you want transfer Rs$amount to ${user.name} ?")
            .setPositiveButton("Yes"){dialoginterface,i->
                status="Success"
                if(mUser.balance>amount.toInt()) {
                    transaction(amount, fromName, user.name, status)
                }
                else{
                    status="Failed"
                    transaction(amount, fromName, user.name, status)
                }
            }
            .setNegativeButton("No"){dialoginterface,i->
                status="Failed"
                transaction(amount,fromName,user.name,status)
            }.create()
        userDialog.show()
    }


    private fun transaction(amount:String, from:String, to:String,status:String){
       val calendar = Calendar.getInstance()
       val simpleDateFormat = SimpleDateFormat("dd.LLLL.yyyy HH:mm:ss aaa ")
       val dateTime = simpleDateFormat.format(calendar.time).toString()
        val db=DatabaseHandler(this)
       val response= db.transactionData(amount,from,to,status,dateTime)
        if (response>-1){
            //transaction successful
            if (status=="Failed"){
                if(amount.toDouble()>mUser.balance){
                Toast.makeText(this,"Transaction $status, Insufficient amount!!!!",Toast.LENGTH_SHORT).show()
                    }
                else{
                    Toast.makeText(this,"Transaction $status!!!!",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Transaction $status!!!!",Toast.LENGTH_SHORT).show()
            }
            Log.d("transaction show user","amount $amount , userA ${aUser.balance}  and total ${(amount.toDouble()+aUser.balance)}  and id${aUser.id}")

            if (status != "Failed") {
                db.updateAmount(aUser.id, amount.toDouble() + aUser.balance)
                db.updateAmount(mUser.id,mUser.balance-amount.toDouble())
            }

            val intent=Intent(this,MainActivity::class.java)
            intent.putExtra("tUserId",aUser.id.toString())
            intent.putExtra("tAmount",amount)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        else{
            //transaction Failed
            Toast.makeText(this,"Transaction Failed!!!!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClicked(user: User) {
       showDialog(user)
        aUser=user
    }
}