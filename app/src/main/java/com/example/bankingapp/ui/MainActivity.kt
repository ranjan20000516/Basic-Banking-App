package com.example.bankingapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.R
import com.example.bankingapp.ui.adapter.UserAdapter
import com.example.bankingapp.ui.adapter.onItemClickedListerner
import com.example.bankingapp.ui.database.DatabaseHandler
import com.example.bankingapp.ui.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),onItemClickedListerner {
    lateinit var userAdapter:UserAdapter

     lateinit var amount:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uid= intent.getStringExtra("tUserId").toString()
        amount=intent.getStringExtra("tAmount").toString()
        setupRecyclerView(uid)

        Log.d("main Activity","id :$uid")
        getUserList()
    }

    private fun getUserList() {
        val databaseHandler=DatabaseHandler(this)
        val userList=databaseHandler.getAllUser()
        userAdapter.differ.submitList(userList)
    }

    private fun setupRecyclerView(uid:String) {
        userAdapter= UserAdapter(this)
        recycleView.adapter=userAdapter
        recycleView.layoutManager=LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.transaction-> startActivity(Intent(this,TransactionActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(user: User) {
        val intent= Intent(this,ShowProfileActivity::class.java)
        intent.putExtra("user",user)
        startActivity(intent)
    }
}