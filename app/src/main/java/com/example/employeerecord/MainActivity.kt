package com.example.employeerecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var employeeDBHelper : EmployeesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeDBHelper = EmployeesDBHelper(this)
    }

    fun addEmployee(view : View){
        val employeeId = this.txt_employee_id.text.toString()
        val employeeName = this.txt_employee_name.text.toString()
        val employeeAge = this.txt_employee_age.text.toString()

        val result = employeeDBHelper.insertEmployee(EmployeeModel(employeeId = employeeId,employeeName = employeeName,employeeAge = employeeAge))

        this.txt_employee_id.setText("")
        this.txt_employee_name.setText("")
        this.txt_employee_age.setText("")
        this.txt_result_title.text = "Added result : " + result
    }

    fun showEmployeeInformation(view:View){
        val employees = employeeDBHelper.readAllEmployees()
        this.ll_entries.removeAllViews()
        employees.forEach {
            val tv_textview = TextView(this)
            tv_textview.text = it.employeeName.toString() + "-" + it.employeeAge.toString()
            this.ll_entries.addView(tv_textview)
        }
    }

    fun showEmployeeInformationbyId(view:View){
        val employeeId = this.txt_employee_id.text.toString()
        this.ll_entries.removeAllViews()
        val employees = employeeDBHelper.readEmployeeById(employeeId)
        employees.forEach {
            val tv_textview = TextView(this)
            tv_textview.text = it.employeeName.toString() + "-" + it.employeeAge.toString()
            this.ll_entries.addView(tv_textview)
        }
    }

    fun deleteEmployeeInformation(view:View){
        val employeeId = this.txt_employee_id.text.toString()
        val result = employeeDBHelper.deleteUser(employeeId)
    }
}
