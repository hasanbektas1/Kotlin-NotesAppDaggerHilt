package com.hasanbektas.notesappdaggerhilt.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hasanbektas.notesappdaggerhilt.R
import com.hasanbektas.notesappdaggerhilt.adapter.NotesAdapter
import com.hasanbektas.notesappdaggerhilt.databinding.FragmentMainBinding
import com.hasanbektas.notesappdaggerhilt.viewmodel.NotesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment @Inject constructor(
    val noteAdapter: NotesAdapter,
): Fragment(){

    private lateinit var viewModel: NotesViewModel

    private lateinit var dataBinding : FragmentMainBinding

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder, ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, ) {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Silmek istediğinize emin misiniz?")
                .setPositiveButton("Evet", DialogInterface.OnClickListener { dialog, which ->
                    val layoutPosition = viewHolder.layoutPosition
                    val selectedNot = noteAdapter.noteList[layoutPosition]
                    viewModel.deleteNot(selectedNot)
                    Toast.makeText(requireContext(),"silindi '${selectedNot.title}'", Toast.LENGTH_LONG).show()
                })
                .setNegativeButton("Hayır", DialogInterface.OnClickListener { dialog, which ->
                    noteAdapter.updateNoteList()
                })
            dialogBuilder.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)

        subscribeToObservers()
        searchView()

        dataBinding.recyclerViewMain.adapter = noteAdapter
        dataBinding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(dataBinding.recyclerViewMain)

        dataBinding.fab.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun subscribeToObservers() {

        viewModel.noteList.observe(viewLifecycleOwner, Observer {noteList ->
            noteAdapter.searchNoteList(noteList.reversed())
        })
    }
    private fun searchNotes(query: String?) {
        val searchQuery = "%$query%"

        viewModel.searchNote(searchQuery).observe(viewLifecycleOwner) { list ->
            noteAdapter.searchNoteList(list.reversed())
        }
    }
    private fun searchView() {
        dataBinding.searchEditText.addTextChangedListener {
            var job : Job?= null
            job?.cancel()

            job = lifecycleScope.launch {
                it?.let {
                    if (it.toString().isNotEmpty()){
                        searchNotes(it.toString())
                    }else{
                        subscribeToObservers()
                    }
                }
            }
        }
    }
}