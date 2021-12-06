package usn.gruppe7.eskapader_android

import android.content.ContentValues
import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import usn.gruppe7.eskapader_android.databinding.FragmentLoggInnBinding


class LoggInnFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoggInnBinding>(inflater,R.layout.fragment_logg_inn,container,false)

        binding.textView2.setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_loggInnFragment_to_opprettBrukerFragment2)
        }



        return binding.root
    }
}