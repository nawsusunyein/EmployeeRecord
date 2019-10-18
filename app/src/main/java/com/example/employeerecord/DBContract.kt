package com.example.employeerecord

import android.provider.BaseColumns

object DBContract{
    class EmployeeEntry : BaseColumns {
        companion object{
            val TABLE_NAME = "employee"
            val COLUMN_EMPLOYEE_ID = "employeeId"
            val COLUMN_EMPLOYEE_NAME = "employeeName"
            val COLUMN_EMPLOYEE_AGE = "employeeAge"
        }
    }
}