package com.example.expensecalculator

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ExpenseListFragment : Fragment() {
    private val expenseList = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter
    private var footerFragment: FooterFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expense_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expenseNameInput = view.findViewById<EditText>(R.id.expenseNameInput)
        val expenseAmountInput = view.findViewById<EditText>(R.id.expenseAmountInput)
        val addExpenseButton = view.findViewById<Button>(R.id.addExpenseButton)
        val financialTipsButton = view.findViewById<Button>(R.id.financialTipsButton)
        val expenseRecyclerView = view.findViewById<RecyclerView>(R.id.expenseRecyclerView)

        adapter = ExpenseAdapter(expenseList,
            { position -> showExpenseDetails(position) },
            { position -> deleteExpense(position) }
        )
        expenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        expenseRecyclerView.adapter = adapter

        loadSampleExpenses()
        loadExpensesFromFile()
        updateTotalAmount()

        footerFragment = childFragmentManager.findFragmentById(R.id.footerFragment) as? FooterFragment

        addExpenseButton.setOnClickListener {
            val name = expenseNameInput.text.toString().trim()
            val amount = expenseAmountInput.text.toString().trim().toDoubleOrNull()
            val date = "2025-03-17"

            if (name.isEmpty() || amount == null) {
                Toast.makeText(requireContext(), "Please enter valid Name and Amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newExpense = Expense(name, amount, date)
            expenseList.add(newExpense)
            adapter.notifyDataSetChanged()
            saveExpensesToFile()
            updateTotalAmount()

            // Clear input fields
            expenseNameInput.text.clear()
            expenseAmountInput.text.clear()
        }

        financialTipsButton.setOnClickListener {
            val browserIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.financialtips.com"))
            startActivity(browserIntent)
        }
    }

    private fun showExpenseDetails(position: Int) {
        val expense = expenseList[position]
        val bundle = Bundle().apply {
            putString("expenseName", expense.name)
            putString("expenseAmount", expense.amount.toString())
            putString("expenseDate", expense.date)
        }

        findNavController().navigate(R.id.expenseDetailsFragment, bundle)
    }

    private fun deleteExpense(position: Int) {
        val removedExpense = expenseList[position]
        Toast.makeText(requireContext(), "Deleted: ${removedExpense.name}", Toast.LENGTH_SHORT).show()
        expenseList.removeAt(position)
        adapter.notifyDataSetChanged()
        saveExpensesToFile()
        updateTotalAmount()
    }

    private fun saveExpensesToFile() {
        val jsonArray = JSONArray()
        for (expense in expenseList) {
            val obj = JSONObject()
            obj.put("name", expense.name)
            obj.put("amount", expense.amount)
            obj.put("date", expense.date)
            jsonArray.put(obj)
        }
        val jsonString = jsonArray.toString()
        try {
            requireContext().openFileOutput("expenses.json", Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadExpensesFromFile() {
        try {
            val file = File(requireContext().filesDir, "expenses.json")
            if (!file.exists()) return
            expenseList.clear()

            val inputStream = FileInputStream(file)
            val reader = InputStreamReader(inputStream)
            val content = reader.readText()
            reader.close()

            val jsonArray = JSONArray(content)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val name = obj.getString("name")
                val amount = obj.getDouble("amount")
                val date = obj.getString("date")
                expenseList.add(Expense(name, amount, date))
            }
            adapter.notifyDataSetChanged()
            updateTotalAmount()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun updateTotalAmount() {
        val total = expenseList.sumOf { it.amount }
        footerFragment?.updateTotalAmount(total)
    }

    private fun loadSampleExpenses() {
        expenseList.add(Expense("Groceries", 250.0, "2025-03-17"))
        expenseList.add(Expense("Rent", 550.0, "2025-03-18"))
        expenseList.add(Expense("Car", 500.0, "2025-03-19"))
        adapter.notifyDataSetChanged()
        updateTotalAmount()
    }

}
