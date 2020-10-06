package com.example.notes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.databinding.FragmentUpdateBinding
import com.example.notes.model.Notes
import com.example.notes.ui.viewmodel.NotesViewModel
import com.example.notes.utils.autoCleared

class UpdateFragment : Fragment() {
    private val navArgs by navArgs<UpdateFragmentArgs>()
    private lateinit var viewModel: NotesViewModel
    private var binding: FragmentUpdateBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        setupViews()
    }

    private fun setupViews() {
        binding.edtTitle.setText(navArgs.notes.title)
        binding.edtDescription.setText(navArgs.notes.description)

        binding.btnUpdate.setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem() {
        val title = binding.edtTitle.text.toString()
        val description = binding.edtDescription.text.toString()

        if (title.length <= binding.inputLayout.counterMaxLength
            && description.isNotEmpty()
            && description.length <= binding.descriptionLayout.counterMaxLength) {

            val updatedNote = Notes(navArgs.notes.id, title, description)
            viewModel.updateNotes(updatedNote)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Task was successfully updated!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}