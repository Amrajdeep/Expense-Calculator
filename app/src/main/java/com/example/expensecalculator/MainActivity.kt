package com.example.expensecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val expenseNames = ArrayList<String>()
    private val expenseAmounts = ArrayList<String>()
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expenseNameInput = findViewById<EditText>(R.id.expenseNameInput)
        val expenseAmountInput = findViewById<EditText>(R.id.expenseAmountInput)
        val addExpenseButton = findViewById<Button>(R.id.addExpenseButton)
        val expenseRecyclerView = findViewById<RecyclerView>(R.id.expenseRecyclerView)
        adapter = ExpenseAdapter(expenseNames, expenseAmounts) { position ->
            deleteExpense(position)
        }
        expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        expenseRecyclerView.adapter = adapter

        addExpenseButton.setOnClickListener {
            val name = expenseNameInput.text.toString().trim()
            val amount = expenseAmountInput.text.toString().trim()

            if(name.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please enter both Name and Amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            expenseNames.add(name)
            expenseAmounts.add(amount)
            adapter.notifyDataSetChanged()


            //clear input fields
            expenseNameInput.text.clear()
            expenseAmountInput.text.clear()
        }



    }
    private fun deleteExpense(position: Int) {
        val removedExpense = expenseNames[position]
        Toast.makeText(this, "Deleted: $removedExpense", Toast.LENGTH_SHORT).show()
        expenseNames.removeAt(position)
        expenseAmounts.removeAt(position)
        adapter.notifyDataSetChanged()


    }
}
