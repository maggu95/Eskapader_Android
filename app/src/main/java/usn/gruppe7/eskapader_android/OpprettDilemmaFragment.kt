package usn.gruppe7.eskapader_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettDilemmaBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding

class OpprettDilemmaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentOpprettDilemmaBinding>(inflater,R.layout.fragment_opprett_dilemma,container,false)



        return binding.root;
    }





}