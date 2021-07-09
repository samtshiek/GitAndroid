package com.samandroid.volleypostproject.DBClass

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.samandroid.volleypostproject.Models.Products

class DBHelper(var mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DATABASE_NAME = "mydbCart"
        const val DATABASE_VERSION = 7
        const val TABLE_NAME = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "product_name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_UNIT = "unit"
        const val COLUMN_DESCRIPTION = "description"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("abc", "onCreateDataBase")
        var createTable =
            "create table $TABLE_NAME ($COLUMN_ID char(50), $COLUMN_IMAGE char(50), $COLUMN_NAME char(50), $COLUMN_PRICE char(50), $COLUMN_MRP char(50), $COLUMN_UNIT char(50), $COLUMN_DESCRIPTION char(50))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("abc", "onUpgradeDatabBase")
        var dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        //db?.execSQL(dropTable)
        onCreate(db)

    }


    fun addProduct(product: Products) {
        var db = writableDatabase
        var contentValue = ContentValues()
        contentValue.put(COLUMN_ID, product._id)
        contentValue.put(COLUMN_NAME, product._productName)
        contentValue.put(COLUMN_PRICE, product._price)
        contentValue.put(COLUMN_IMAGE, product._image)
        contentValue.put(COLUMN_MRP, product._mrp)
        contentValue.put(COLUMN_PRICE, product._price)
        contentValue.put(COLUMN_UNIT, product._unit)
        contentValue.put(COLUMN_DESCRIPTION, product._description)
        db.insert(TABLE_NAME, null, contentValue)
        Log.d("abc", "addProduct")
    }


    fun updateProduct(product: Products) {
        var db = writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COLUMN_ID, product._id)
        contentValues.put(COLUMN_NAME, product._productName)
        contentValues.put(COLUMN_PRICE, product._price)
        contentValues.put(COLUMN_IMAGE, product._image)
        contentValues.put(COLUMN_MRP, product._mrp)
        contentValues.put(COLUMN_PRICE, product._price)
        contentValues.put(COLUMN_UNIT, product._unit)
        contentValues.put(COLUMN_DESCRIPTION, product._description)
        var whereClause = "$COLUMN_ID = ?"
        var whereArg = arrayOf(product._id)
        db.update(TABLE_NAME, contentValues, whereClause, whereArg)
    }


    fun deleteProduct(id: String) {
        var db = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id)
        db.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun readProducts(): ArrayList<Products>{

        Log.d("abc", "onReadDataBase")

        var db = readableDatabase
        var productList: ArrayList<Products> = ArrayList()
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PRICE,
            COLUMN_IMAGE,
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_UNIT,
            COLUMN_DESCRIPTION
        )
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor !=null && cursor.moveToFirst()){
            do{
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var mrp = cursor.getInt(cursor.getColumnIndex(COLUMN_MRP))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var unit = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT))
                var descritption = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                var product = Products(id, name, image, unit,price, mrp, descritption)
                productList.add(product)
            }while (cursor.moveToNext())
        }
        return productList
    }

}