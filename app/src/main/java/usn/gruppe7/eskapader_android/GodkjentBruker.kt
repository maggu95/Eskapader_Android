package usn.gruppe7.eskapader_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import usn.gruppe7.eskapader_android.databinding.FragmentGodkjentBrukerBinding

class GodkjentBruker : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGodkjentBrukerBinding>(inflater,R.layout.fragment_godkjent_bruker,container, false)

        binding.button.setOnClickListener { view : View ->
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}