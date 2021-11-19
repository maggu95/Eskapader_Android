package usn.gruppe7.eskapader_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.R

import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentMinSideBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettDilemmaBinding


class min_aide : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMinSideBinding>(inflater,
            usn.gruppe7.eskapader_android.R.layout.fragment_min_side,container,false)

        val arraySpinner = arrayOf(
            "1", "2", "3", "4", "5", "6", "7"
        )

        //val s = findViewById(R.id.spillS) as Spinner
        //val adapter: ArrayAdapter<String>(this,binding.spillSpinner, arraySpinner)

        //adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        //s.adapter = adapter

        return binding.root
    }

}