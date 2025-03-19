package com.example.expensecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val expenses: MutableList<Expense>,
    private val onShowDetails: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.expenseName)
        val expenseAmount: TextView = itemView.findViewById(R.id.expenseAmount)
        val expenseDate: TextView = itemView.findViewById(R.id.expenseDate)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val showDetailsButton: Button = itemView.findViewById(R.id.showDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.expenseName.text = expense.name
        holder.expenseAmount.text = "$${expense.amount}"
        holder.expenseDate.text = expense.date

        holder.deleteButton.setOnClickListener { onDelete(position) }
        holder.showDetailsButton.setOnClickListener { onShowDetails(position) }
    }

    override fun getItemCount(): Int = expenses.size
}
