package com.example.notes.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.databinding.FragmentListBinding
import com.example.notes.model.Notes
import com.example.notes.ui.adapter.NotesAdapter
import com.example.notes.ui.viewmodel.NotesViewModel
import com.example.notes.utils.SwipeToDelete
import com.example.notes.utils.autoCleared

class ListFragment: Fragment(), NotesAdapter.NotesItemListener {

    private var binding: FragmentListBinding by autoCleared()
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        setupRecyclerView()
        setupObservers()

        setHasOptionsMenu(true)
    }

    private fun setupObservers() {
        viewModel.getAllNotes.observe(viewLifecycleOwner, {
            adapter.setNotes(ArrayList(it))
        })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = NotesAdapter(this)
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter, viewModel))
        itemTouchHelper.attachToRecyclerView(binding.rvNotes)
    }

    private fun deleteAllNotes() {
        viewModel.deleteAllNotes()
    }

    override fun onClickedNotes(notes: Notes) {
        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(notes)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_clearAll -> {
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}