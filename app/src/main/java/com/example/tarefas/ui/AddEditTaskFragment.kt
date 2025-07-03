package com.example.tarefas.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tarefas.data.AppDatabase
import com.example.tarefas.data.Task
import com.example.tarefas.data.TaskRepository
import com.example.tarefas.databinding.FragmentAddEditTaskBinding
import kotlinx.coroutines.launch

class AddEditTaskFragment : Fragment() {

    private var _binding: FragmentAddEditTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: TaskRepository
    private val args by navArgs<AddEditTaskFragmentArgs>()
    private var currentTask: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditTaskBinding.inflate(inflater, container, false)
        val dao = AppDatabase.getDatabase(requireContext()).taskDao()
        repository = TaskRepository(dao)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.taskId != -1) {
            repository.getTaskById(args.taskId).observe(viewLifecycleOwner) { task ->
                task?.let {
                    currentTask = it
                    binding.editTextTitle.setText(it.title)
                    binding.editTextDescription.setText(it.description)

                    // Mostrar o botão de exclusão apenas para tarefas existentes
                    binding.buttonDelete.visibility = View.VISIBLE
                }
            }
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDescription.text.toString()

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Título obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = currentTask?.copy(title = title, description = desc)
                ?: Task(title = title, description = desc)

            lifecycleScope.launch {
                if (currentTask == null)
                    repository.insert(task)
                else
                    repository.update(task)

                findNavController().navigateUp()
            }
        }

        // Configurar o botão de exclusão
        binding.buttonDelete.setOnClickListener {
            currentTask?.let { task ->
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Excluir Tarefa")
                    .setMessage("Tem certeza que deseja excluir esta tarefa?")
                    .setPositiveButton("Sim") { _, _ ->
                        lifecycleScope.launch {
                            repository.delete(task)
                            findNavController().navigateUp()
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
