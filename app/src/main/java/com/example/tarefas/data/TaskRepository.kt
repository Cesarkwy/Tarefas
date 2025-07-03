package com.example.tarefas.data

class TaskRepository(private val dao: TaskDao) {
    suspend fun insert(task: Task) = dao.insert(task)
    suspend fun update(task: Task) = dao.update(task)
    suspend fun delete(task: Task) = dao.delete(task)
    fun getAllTasks() = dao.getAllTasks()
    fun getAllTasksAlphabetically() = dao.getAllTasksAlphabetically()
    fun searchTasks(query: String) = dao.searchTasks(query)
    fun searchTasksAlphabetically(query: String) = dao.searchTasksAlphabetically(query)
    fun getTaskById(taskId: Int) = dao.getTaskById(taskId)
}
