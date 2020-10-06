package com.example.notes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.databinding.FragmentAddBinding
import com.example.notes.model.Notes
import com.example.notes.ui.viewmodel.NotesViewModel

class AddFragment: Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding get() = _binding!!
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        binding.btnAdd.setOnClickListener {
            addNoteToDatabase()
        }
    }

    private fun addNoteToDatabase() {
        val title = binding.edtTitle.text.toString()
        val description = binding.edtDescription.text.toString()

        if (title.length <= binding.inputLayout.counterMaxLength
            && description.isNotEmpty()
            && description.length <= binding.descriptionLayout.counterMaxLength) {

            val notes = Notes(0, title, description)
            viewModel.addNotes(notes)
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
            Toast.makeText(requireContext(), "Task was successfully added", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}