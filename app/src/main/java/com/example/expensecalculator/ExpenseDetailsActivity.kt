package com.example.expensecalculator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        val expenseNameText = findViewById<TextView>(R.id.expenseNameText)
        val expenseAmountText = findViewById<TextView>(R.id.expenseAmountText)
        val expenseDateText = findViewById<TextView>(R.id.expenseDateText)

        val expenseName = intent.getStringExtra("EXPENSE_NAME")
        val expenseAmount = intent.getStringExtra("EXPENSE_AMOUNT")
        val expenseDate = intent.getStringExtra("EXPENSE_DATE")

        expenseNameText.text = "Expense Name: $expenseName"
        expenseAmountText.text = "Amount: $expenseAmount"
        expenseDateText.text = "Date: $expenseDate"
    }
}
