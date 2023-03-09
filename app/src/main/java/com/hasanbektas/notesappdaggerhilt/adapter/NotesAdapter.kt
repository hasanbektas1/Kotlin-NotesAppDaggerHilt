package com.hasanbektas.notesappdaggerhilt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hasanbektas.notesappdaggerhilt.R
import com.hasanbektas.notesappdaggerhilt.databinding.NotesRecyclerviewRowBinding
import com.hasanbektas.notesappdaggerhilt.model.Notes
import com.hasanbektas.notesappdaggerhilt.view.MainFragmentDirections
import kotlinx.android.synthetic.main.notes_recyclerview_row.view.*
import javax.inject.Inject

class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.NotesViewholder>(),NoteClickedListener {

    class NotesViewholder(var viewBinding : NotesRecyclerviewRowBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Notes>(){
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var noteList : List<Notes>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewholder {

        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<NotesRecyclerviewRowBinding>(inflater,
            R.layout.notes_recyclerview_row,parent,false)
        return NotesViewholder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NotesViewholder, position: Int) {

        holder.viewBinding.notesDataBinding = noteList[position]
        holder.viewBinding.clickListener = this

    }
    override fun notesClicked(view: View) {

        val notesIdText = view.noteIdText.text.toString().toInt()
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(notesIdText)
        Navigation.findNavController(view).navigate(action)
    }
    fun updateNoteList(){
        notifyDataSetChanged()
    }

    fun searchNoteList(note: List<Notes>) {
        noteList = note
        notifyDataSetChanged()
    }
}