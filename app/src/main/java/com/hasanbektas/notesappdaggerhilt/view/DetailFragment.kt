package com.hasanbektas.notesappdaggerhilt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hasanbektas.notesappdaggerhilt.R
import com.hasanbektas.notesappdaggerhilt.databinding.FragmentDetailBinding
import com.hasanbektas.notesappdaggerhilt.util.Status
import com.hasanbektas.notesappdaggerhilt.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {

    private lateinit var viewModelDetail : DetailViewModel

    private lateinit var dataBinding : FragmentDetailBinding

    private var noteDataId = 0

    val sdf = SimpleDateFormat("dd/M/yyyy")
    val currentDate = sdf.format(Date())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            noteDataId = DetailFragmentArgs.fromBundle(it).ArguNoteId
        }

        viewModelDetail = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)


        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        subscribeToObservers()

        viewModelDetail.getDataSelectedNote(noteDataId)


        dataBinding.saveButton.setOnClickListener {

            viewModelDetail.makeNot(dataBinding.detailTitleText.text.toString(),
                dataBinding.detailNoteText.text.toString(),currentDate)
        }
    }

    private fun subscribeToObservers(){

        viewModelDetail.insertNotMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Kaydedildi", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModelDetail.resetInsertNotMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message?: "Tekrar Deneyiniz", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModelDetail.selectedNotLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {selectedNote ->

            selectedNote?.let {

                dataBinding.selectedNot = it
            }
        })
    }
}