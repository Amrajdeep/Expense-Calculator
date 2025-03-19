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
    private val expenseList = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter
    private var footerFragment: FooterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("ActivityLifecycle", "onCreate called")

        val expenseNameInput = findViewById<EditText>(R.id.expenseNameInput)
        val expenseAmountInput = findViewById<EditText>(R.id.expenseAmountInput)
        val addExpenseButton = findViewById<Button>(R.id.addExpenseButton)
        val financialTipsButton = findViewById<Button>(R.id.financialTipsButton)
        val expenseRecyclerView = findViewById<RecyclerView>(R.id.expenseRecyclerView)

        adapter = ExpenseAdapter(expenseList,
            { position -> showExpenseDetails(position) },
            { position -> deleteExpense(position) }
        )
        expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        expenseRecyclerView.adapter = adapter

        loadFragments()

        addExpenseButton.setOnClickListener {
            val name = expenseNameInput.text.toString().trim()
            val amount = expenseAmountInput.text.toString().trim().toDoubleOrNull()
            val date = "2025-03-17"

            if (name.isEmpty() || amount == null) {
                Toast.makeText(this, "Please enter valid Name and Amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newExpense = Expense(name, amount, date)
            expenseList.add(newExpense)
            adapter.notifyDataSetChanged()

            updateTotalExpense()

            // Clear input fields
            expenseNameInput.text.clear()
            expenseAmountInput.text.clear()
        }


        financialTipsButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.financialtips.com"))
            startActivity(browserIntent)
        }
    }

    private fun loadFragments() {
        val fragmentManager = supportFragmentManager

        val headerFragment = HeaderFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.headerContainer, headerFragment)
            .commit()


        footerFragment = supportFragmentManager.findFragmentById(R.id.footerContainer) as? FooterFragment
        if (footerFragment == null) {
            footerFragment = FooterFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.footerContainer, footerFragment!!)
                .commit()
        }
    }

    private fun updateTotalExpense() {
        val total = expenseList.sumOf { it.amount }
        footerFragment?.updateTotalAmount(total)
    }

    private fun showExpenseDetails(position: Int) {
        val expense = expenseList[position]
        val intent = Intent(this, ExpenseDetailsActivity::class.java).apply {
            putExtra("EXPENSE_NAME", expense.name)
            putExtra("EXPENSE_AMOUNT", expense.amount.toString())
            putExtra("EXPENSE_DATE", expense.date)
        }
        startActivity(intent)
    }

    private fun deleteExpense(position: Int) {
        val removedExpense = expenseList[position]
        Toast.makeText(this, "Deleted: ${removedExpense.name}", Toast.LENGTH_SHORT).show()
        expenseList.removeAt(position)
        adapter.notifyDataSetChanged()
        updateTotalExpense()
    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifecycle", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifecycle", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle", "onDestroy called")
    }
}
