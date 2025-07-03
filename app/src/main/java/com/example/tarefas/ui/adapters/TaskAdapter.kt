package com.example.tarefas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarefas.R
import com.example.tarefas.data.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private val onItemClick: (Task) -> Unit,
    private val onFavoriteClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.taskTitle)
        val description: TextView = itemView.findViewById(R.id.taskDescription)
        val star: ImageView = itemView.findViewById(R.id.taskStar)
        val delete: ImageView = itemView.findViewById(R.id.taskDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.description.text = task.description

        holder.itemView.setOnClickListener { onItemClick(task) }
        holder.star.setOnClickListener { onFavoriteClick(task) }
        holder.delete.setOnClickListener { onDeleteClick(task) }
    }

    override fun getItemCount() = tasks.size

    fun updateList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }
}
