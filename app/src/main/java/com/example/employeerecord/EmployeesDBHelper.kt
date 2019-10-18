package com.example.employeerecord

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class EmployeesDBHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }

    fun insertEmployee(employee : EmployeeModel) : Boolean{

        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_ID,employee.employeeId)
        values.put(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME,employee.employeeName)
        values.put(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_AGE,employee.employeeAge)

        val newRowId = db.insert(DBContract.EmployeeEntry.TABLE_NAME,null,values)
        return true
    }

    fun readAllEmployees() : ArrayList<EmployeeModel>{
        val employeeList = ArrayList<EmployeeModel>()

        val db = writableDatabase
        var cursor : Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM " + DBContract.EmployeeEntry.TABLE_NAME, null)
        }catch (e : SQLiteException){
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var employeeName : String
        var employeeId : String
        var employeeAge : String

        if(cursor!!.moveToFirst()){
            while (cursor.isAfterLast == false){
                employeeId = cursor.getString(cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_ID))
                employeeAge = cursor.getString(cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_AGE))
                employeeName = cursor.getString(cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME))
                employeeList.add(EmployeeModel(employeeId,employeeName,employeeAge))
                cursor.moveToNext()
            }
        }

        return employeeList
    }

    fun readEmployeeById(userId : String) : ArrayList<EmployeeModel>{
        var employeeList = ArrayList<EmployeeModel>()
        val db = writableDatabase
        var cursor : Cursor? = null

        try {
            cursor = db.rawQuery("select * from " + DBContract.EmployeeEntry.TABLE_NAME + " WHERE " + DBContract.EmployeeEntry.COLUMN_EMPLOYEE_ID + "='" + userId + "' ", null)
        }catch (e : SQLiteException){
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var employeeName : String
        var employeeAge : String

        if(cursor!!.moveToFirst()){
            while (cursor.isAfterLast == false){
                employeeAge = cursor.getString(cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_AGE))
                employeeName = cursor.getString(cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME))
                employeeList.add(EmployeeModel(userId,employeeName,employeeAge))
                cursor.moveToNext()
            }
        }

        return employeeList
    }

    fun deleteUser(employeeId : String) : Boolean{
        val db = writableDatabase
        val selection = DBContract.EmployeeEntry.COLUMN_EMPLOYEE_ID + "=" + employeeId
        val args = arrayOf(employeeId)
        db.delete(DBContract.EmployeeEntry.TABLE_NAME,selection,null)
        return true
    }
    companion object{
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "EmployeeRecord.db"

        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + DBContract.EmployeeEntry.TABLE_NAME + "(" + DBContract.EmployeeEntry.COLUMN_EMPLOYEE_ID + " TEXT PRIMARY KEY," + DBContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME +
                " TEXT," + DBContract.EmployeeEntry.COLUMN_EMPLOYEE_AGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.EmployeeEntry.TABLE_NAME
    }


}

