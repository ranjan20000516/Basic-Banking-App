package com.example.bankingapp.ui.adapter

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
import com.example.bankingapp.ui.model.User
import kotlin.math.roundToInt

class UserAdapter(private val listerner: onItemClickedListerner): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userName=itemView.findViewById<TextView>(R.id.userNameText)
        val balanceText=itemView.findViewById<TextView>(R.id.balanceText)
        val phoneText:TextView=itemView.findViewById(R.id.phoneNumberTextUser)

    }

    private val differCallback= object : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ= AsyncListDiffer(this@UserAdapter,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewHolder= UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false))
        viewHolder.itemView.setOnClickListener {
            listerner.onItemClicked(differ.currentList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Log.d("User Adapter","bind")
        Log.d("User Adapter","bind")

        val currentUser=differ.currentList[position]
        holder.userName.text=currentUser.name
        val roundAmount=(currentUser.balance*100.0).roundToInt()/100.0
        holder.balanceText.text="Rs "+roundAmount.toString()
        holder.phoneText.text="+91 "+ currentUser.phoneNo

        Log.d("User Adapter","userNAme ${currentUser.name} and id ${currentUser.id}")
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}


interface onItemClickedListerner{
    fun onItemClicked(user:User)
}