package com.example.expensecalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val expenseNames = ArrayList<String>()
    private val expenseAmounts = ArrayList<String>()
    private val expenseDates = ArrayList<String>()
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("ActivityLifecycle", "onCreate called")

        val expenseNameInput = findViewById<EditText>(R.id.expenseNameInput)
        val expenseAmountInput = findViewById<EditText>(R.id.expenseAmountInput)
        val addExpenseButton = findViewById<Button>(R.id.addExpenseButton)
        val showDetailsButton = findViewById<Button>(R.id.showDetailsButton)
        val expenseRecyclerView = findViewById<RecyclerView>(R.id.expenseRecyclerView)
        adapter = ExpenseAdapter(expenseNames, expenseAmounts, expenseDates) { position ->
            showExpenseDetails(position)
        }
        expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        expenseRecyclerView.adapter = adapter

        addExpenseButton.setOnClickListener {
            val name = expenseNameInput.text.toString().trim()
            val amount = expenseAmountInput.text.toString().trim()
            val date = "2025-03-17"

            if(name.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please enter both Name and Amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            expenseNames.add(name)
            expenseAmounts.add(amount)
            expenseDates.add(date)
            adapter.notifyDataSetChanged()


            //clear input fields
            expenseNameInput.text.clear()
            expenseAmountInput.text.clear()
        }
        showDetailsButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.financialtips.com"))
            startActivity(browserIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifecycle", "onStart called")
    }
    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "onResume called")
    }
    override fun onPause(){
        super.onPause()
        Log.d("ActivityLifecycle", "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifecycle","onStop called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle","onDestroy called")
    }
    private fun showExpenseDetails(position: Int) {
        val intent = Intent(this, ExpenseDetailsActivity::class.java)
        intent.putExtra("EXPENSE_NAME", expenseNames[position])
        intent.putExtra("EXPENSE_AMOUNT", expenseAmounts[position])
        intent.putExtra("EXPENSE_DATE", expenseDates[position])
        startActivity(intent)
    }



    }

    /*private fun deleteExpense(position: Int) {
        val removedExpense = expenseNames[position]
        Toast.makeText(this, "Deleted: $removedExpense", Toast.LENGTH_SHORT).show()
        expenseNames.removeAt(position)
        expenseAmounts.removeAt(position)
        adapter.notifyDataSetChanged()


    }**/

