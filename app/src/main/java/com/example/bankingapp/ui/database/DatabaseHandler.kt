package com.example.bankingapp.ui.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.bankingapp.ui.model.Transaction
import com.example.bankingapp.ui.model.User
import java.lang.Exception

class DatabaseHandler(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME="bank.db"
        private const val DATABASE_VERSION=1
        private const val TABLE1= "user_table"
        private const val COSTUMER_ID="_id"
        private const val COSTUMER_NAME="name"
        private const val COSTUMER_BALANCE="balance"
        private const val COSTUMER_EMAIL="email"
        private const val COSTUMER_ACCOUNT_NO="account_no"
        private const val COSTUMER_IFSC_CODE="ifsc_code"
        private const val COSTUMER_ADDRESS="address"
        private const val COSTUMER_PHONE_NUMBER="phone_no"


        //transaction table
        private const val TABLE2="transaction_table"
        private const val COLUMN_TRANSACTION_ID="transaction_id"
        private const val COLUMN_AMOUNT="amount"
        private const val COLUMN_FROM_NAME="from_name"
        private const val COLUMN_TO_NAME="to_name"
        private const val COLUMN_STATUS="status"
        private const val COLUMN_DATE="date"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(" CREATE TABLE " + TABLE1 + "(" + COSTUMER_ID + " INTEGER PRIMARY KEY , " +
                       COSTUMER_NAME + " TEXT, " +
                       COSTUMER_ACCOUNT_NO + " VARCHAR, " +
                       COSTUMER_IFSC_CODE + " VARCHAR, " +
                       COSTUMER_EMAIL + " VARCHAR, " +
                       COSTUMER_BALANCE + " DECIMAL, " +
                       COSTUMER_ADDRESS + " TEXT, " +
                       COSTUMER_PHONE_NUMBER + " TEXT" + ")")


        db?.execSQL(" CREATE TABLE " + TABLE2 + "(" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_AMOUNT + " DECIMAL, " +
                       COLUMN_FROM_NAME + " TEXT, " +
                       COLUMN_TO_NAME + " TEXT, " +
                       COLUMN_STATUS + " TEXT, " +
                       COLUMN_DATE + " TEXT " + ")")

        db?.execSQL("insert into user_table values(1 ,'Kirtika Yadav','XXXXXXXX8594','ABC09876543','kirti123@gmail.com',9472.12,'Durgapur','1234567890')")
        db?.execSQL("insert into user_table values(2 ,'Anuj Kumar','XXXXXXXX7845','ABD09876534','anuj@gmail.com',1125.99,'Kolkata','1234548395')")
        db?.execSQL("insert into user_table values(3 ,'Aniket Singh','XXXXXXXX4712','XFE09842754','aniket450@gmail.com',9000.60,'Kolkata','8924367581')")
        db?.execSQL("insert into user_table values(4 ,'Rahul RuiDas','XXXXXXXX565','cde09874934','rahul16@gmail.com',75.09,'Asansol','4759316785')")
        db?.execSQL("insert into user_table values(5 ,'Suvankar Tudu','XXXXXXX7898','AGF098745316','suvankar852@gmail.com',25884.90,'Durgapur','7892541365')")
        db?.execSQL("insert into user_table values(6 ,'Aryan Agarwal','XXXXXXXX1234','BKD09876534','aryan@gmail.com',4500.40,'Kolkata','4597861325')")
        db?.execSQL("insert into user_table values(7 ,'Abhishek Singh','XXXXXXXX8787','ABD098765128','abhishekad@gmail.com',7000.600,'Andal','9857642879')")
        db?.execSQL("insert into user_table values(8 ,'Rohit Sharma','XXXXXXXX9696','XTX09874454','rohitsharma@gmail.com',912.12,'Buranpur','7845965872')")
        db?.execSQL("insert into user_table values(9 ,'Arnab Singh','XXXXXXXX7323','ACV098785134','arnab55@gmail.com',445.110,'Durgapur','54897524687')")
        db?.execSQL("insert into user_table values(10 ,'Gopal Sardha','XXXXXXXX4561','AFR09874566','gopal@gmail.com',11145.850,'Buranpur','1234548395')")
        db?.execSQL("insert into user_table values(11 ,'Naman Mathur','XXXXXXXX2232','ABV098747895','namanthakur@gmail.com',7859.480,'Andal','8899726135')")
        db?.execSQL("insert into user_table values(12 ,'Pari Kumari','XXXXXXXX8521','CCD09874456','pari1546@gmail.com',4856.00,'Durgapur','7895468795')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+ TABLE1 )
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE2)
        onCreate(db)
    }

    fun getAllUser():ArrayList<User>{
        val userList:ArrayList<User> = ArrayList()
        val selectQuery="SELECT * FROM $TABLE1"
        val db=this.readableDatabase
        var cursor: Cursor? =null

        try {
            cursor=db.rawQuery(selectQuery,null)
        }catch (e:SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var name:String
        var accountNo:String
        var ifscCode:String
        var email:String
        var balance:Double
        var address:String
        var phoneNo:String
        if (cursor.moveToFirst()){
            do {
                id=cursor.getInt(cursor.getColumnIndexOrThrow(COSTUMER_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_NAME))
                accountNo=cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_ACCOUNT_NO))
                ifscCode = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_IFSC_CODE))
                email = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_EMAIL))
                balance = cursor.getDouble(cursor.getColumnIndexOrThrow(COSTUMER_BALANCE))
                address = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_ADDRESS))
                phoneNo = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_PHONE_NUMBER))
                userList.add(User(id,name,accountNo,ifscCode, email, balance, address, phoneNo))
            }while (cursor.moveToNext())
        }
        return userList
    }

    fun showUserToTransferMoney( cId:Int):ArrayList<User>{
        val userList:ArrayList<User> = ArrayList()
        val selectQuery="SELECT * FROM $TABLE1"
        val db=this.readableDatabase
        var cursor: Cursor? =null

        try {
            cursor=db.rawQuery(selectQuery,null)
        }catch (e:SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var name:String
        var accountNo:String
        var ifscCode:String
        var email:String
        var balance:Double
        var address:String
        var phoneNo:String
        if (cursor.moveToFirst()){
            do {
                id=cursor.getInt(cursor.getColumnIndexOrThrow(COSTUMER_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_NAME))
                accountNo=cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_ACCOUNT_NO))
                ifscCode = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_IFSC_CODE))
                email = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_EMAIL))
                balance = cursor.getDouble(cursor.getColumnIndexOrThrow(COSTUMER_BALANCE))
                address = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_ADDRESS))
                phoneNo = cursor.getString(cursor.getColumnIndexOrThrow(COSTUMER_PHONE_NUMBER))
                if (cId == id){
                    continue
                }
                userList.add(User(id,name,accountNo,ifscCode, email, balance, address, phoneNo))
            }while (cursor.moveToNext())
        }
        return userList
    }

    fun updateUser(user:User){

    }

    fun transactionData(amount:String,fromName:String,toName:String,status:String,date:String):Long{
        val db=this.writableDatabase
        val cv=ContentValues()
        cv.put(COLUMN_AMOUNT,amount)
        cv.put(COLUMN_FROM_NAME,fromName)
        cv.put(COLUMN_TO_NAME,toName)
        cv.put(COLUMN_STATUS,status)
        cv.put(COLUMN_DATE,date)
        val result:Long=db.insert(TABLE2,null,cv)
        db.close()
       return result
    }

    fun updateAmount(costumerId:Int,amount:Double){
        val db=this.writableDatabase
        db.execSQL("update user_table set balance = " + amount + " where _id = " +costumerId)
    }

    fun getAllTransaction():ArrayList<Transaction>{
        val userList:ArrayList<Transaction> = ArrayList()
        val selectQuery="SELECT * FROM $TABLE2"
        val db=this.readableDatabase
        var cursor: Cursor? =null

        try {
            cursor=db.rawQuery(selectQuery,null)
        }catch (e:SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var fromName:String
        var toName:String
        var amount:Double
        var status:String
        var date:String
        Log.d("database","call get user")

        if (cursor.moveToFirst()){
            do {
                id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRANSACTION_ID))
               fromName=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FROM_NAME))
                toName=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TO_NAME))
                amount=cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                status=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                date=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                userList.add(Transaction(id,amount,toName,fromName,status,date))
            }while (cursor.moveToNext())
        }
        return userList
    }
}