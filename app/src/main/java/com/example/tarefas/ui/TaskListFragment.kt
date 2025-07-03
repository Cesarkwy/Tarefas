package com.example.tarefas.ui

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarefas.R
import com.example.tarefas.data.AppDatabase
import com.example.tarefas.data.TaskRepository
import com.example.tarefas.databinding.FragmentTaskListBinding
import com.example.tarefas.ui.adapters.TaskAdapter
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TaskAdapter
    private lateinit var repository: TaskRepository
    private var isAlphabeticalOrder = false
    private var currentQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val dao = AppDatabase.getDatabase(requireContext()).taskDao()
        repository = TaskRepository(dao)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TaskAdapter(
            emptyList(),
            onItemClick = { task ->
                val action = TaskListFragmentDirections
                    .actionTaskListFragmentToAddEditTaskFragment(task.id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { task ->
                val updated = task.copy(isFavorite = !task.isFavorite)
                lifecycleScope.launch { repository.update(updated) }
            },
            onDeleteClick = { task ->
                lifecycleScope.launch { repository.delete(task) }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addEditTaskFragment)
        }

        // Carregar tarefas inicialmente
        loadTasks()

        // Configurar busca
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true
            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText ?: ""
                loadTasks()
                return true
            }
        })

        // Configurar ordenação
        binding.buttonSort.setOnClickListener { view ->
            showSortingMenu(view)
        }
    }

    private fun showSortingMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.sort_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.sort_date -> {
                    isAlphabeticalOrder = false
                    loadTasks()
                    true
                }
                R.id.sort_alphabetical -> {
                    isAlphabeticalOrder = true
                    loadTasks()
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    private fun loadTasks() {
        if (currentQuery.isEmpty()) {
            if (isAlphabeticalOrder) {
                repository.getAllTasksAlphabetically().observe(viewLifecycleOwner) {
                    adapter.updateList(it)
                }
            } else {
                repository.getAllTasks().observe(viewLifecycleOwner) {
                    adapter.updateList(it)
                }
            }
        } else {
            if (isAlphabeticalOrder) {
                repository.searchTasksAlphabetically(currentQuery).observe(viewLifecycleOwner) {
                    adapter.updateList(it)
                }
            } else {
                repository.searchTasks(currentQuery).observe(viewLifecycleOwner) {
                    adapter.updateList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
