package com.example.bankingapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.R
import com.example.bankingapp.ui.adapter.TransactionAdapter
import com.example.bankingapp.ui.database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

     lateinit var transactionAdapter: TransactionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        Log.d("transaction Activity","main")
        setUpRecycleView()

        showTransaction()
    }

    private fun showTransaction() {
        val db=DatabaseHandler(this)
        val transactionList=db.getAllTransaction().reversed()
        Log.d("transaction Activity","database")
        Log.d("transaction Activity","$transactionList")
        transactionAdapter.differ.submitList(transactionList)
        Log.d("transaction Activity","database list")
    }

    private fun setUpRecycleView() {
        transactionAdapter= TransactionAdapter()
        Log.d("transaction Activity","recycle  view")

        recycleView.adapter=transactionAdapter
        recycleView.layoutManager=LinearLayoutManager(this)
    }
}