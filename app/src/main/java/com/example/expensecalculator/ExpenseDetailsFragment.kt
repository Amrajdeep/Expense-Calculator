package com.example.expensecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExpenseDetailsFragment : Fragment() {

    override fun onCreateView(inflator: LayoutInflater, container:ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment_expense_details, container, false )

        val expenseName = arguments?.getString("expenseName") ?: "No Name"
        val expenseAmount = arguments?.getString("expenseAmount") ?: "No Amount"
        val expenseDate = arguments?.getString("expenseDate") ?: "No Date"

        view.findViewById<TextView>(R.id.expenseNameText).text = "Expense Name: $expenseName"
        view.findViewById<TextView>(R.id.expenseAmountText).text = "Amount: $expenseAmount"
        view.findViewById<TextView>(R.id.expenseDateText).text = "Date: $expenseDate"

        return view
    }
}
