package com.example.bankingapp.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.ui.model.Transaction
import kotlin.math.roundToInt


class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fromName: TextView =itemView.findViewById(R.id.fromNameText)
        val toName:TextView=itemView.findViewById(R.id.toNameText)
        val amount:TextView=itemView.findViewById(R.id.transferAmountText)
        val status:TextView=itemView.findViewById(R.id.statusText)
        val date:TextView=itemView.findViewById(R.id.dateText)
        val transactionId:TextView=itemView.findViewById(R.id.transactionIdText)
        val statusIcon:ImageView=itemView.findViewById(R.id.statusIcon)
    }

     private val differCallBack= object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
           return oldItem.transactionId == newItem.transactionId
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

    val differ=AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        Log.d("transaction activity","adpter main hai")
       return TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction,parent,false))
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        Log.d("transaction Adapter","bind")

        val currentTransaction=differ.currentList[position]
        holder.transactionId.text=currentTransaction.transactionId.toString()
        holder.fromName.text=currentTransaction.fromName
        holder.toName.text=currentTransaction.toName
        val roundAmount=(currentTransaction.amount*100.0).roundToInt()/100.0
        holder.amount.text=roundAmount.toString()
        holder.date.text=currentTransaction.date
        holder.status.text=currentTransaction.status
        if (currentTransaction.status == "Failed"){
            holder.status.setTextColor(Color.RED)
            holder.amount.setTextColor(Color.RED)
            holder.statusIcon.setImageResource(R.drawable.ic_failed)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}