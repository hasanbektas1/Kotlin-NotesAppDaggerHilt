package com.hasanbektas.notesappdaggerhilt.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hasanbektas.notesappdaggerhilt.adapter.NotesAdapter
import javax.inject.Inject

class NotesFragmentFactory @Inject constructor(
    private val notesAdapter : NotesAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            MainFragment::class.java.name -> MainFragment(notesAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}