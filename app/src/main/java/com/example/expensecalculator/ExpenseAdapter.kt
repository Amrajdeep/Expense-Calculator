package com.example.expensecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val names: ArrayList<String>,
    private val amounts: ArrayList<String>,
    private val deleteExpense: (Int) -> Unit
) : RecyclerView.Adapter<com.example.expensecalculator.ExpenseAdapter.ExpenseViewHolder>()
{

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.expenseName)
        val amountText: TextView = view.findViewById(R.id.expenseAmount)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.expensecalculator.ExpenseAdapter.ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return com.example.expensecalculator.ExpenseAdapter.ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: com.example.expensecalculator.ExpenseAdapter.ExpenseViewHolder, position: Int) {
        holder.nameText.text = names[position]
        holder.amountText.text = "$${amounts[position]}"
        holder.deleteButton.setOnClickListener {
            deleteExpense(position)
        }
    }

    override fun getItemCount(): Int = names.size
}