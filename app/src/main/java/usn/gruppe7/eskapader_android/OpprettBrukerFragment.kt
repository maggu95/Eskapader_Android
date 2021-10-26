package usn.gruppe7.eskapader_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettBrukerBinding


class OpprettBrukerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOpprettBrukerBinding>(inflater,R.layout.fragment_opprett_bruker,container,false)



        binding.bekreftBrukerbtn.setOnClickListener { view : View ->
            if (binding.lagBrukernavnInput.text.toString().isEmpty() || binding.lagPassordInput.text.toString().isEmpty()) {
                Toast.makeText(context, "Brukernavn og passord kan ikke være tom!", Toast.LENGTH_SHORT).show()
            }
            else {
                val username = binding.lagBrukernavnInput.text.toString()
                val password = binding.lagPassordInput.text.toString()
                if (username.contains(" ") || password.contains(" "))
                    Toast.makeText(context, "Kan ikke ha mellomrom i brukernavn eller passord!", Toast.LENGTH_SHORT).show()
                else if (!gyldigPassord(password)) {
                    Toast.makeText(context, "Passord må inneholde et tall!", Toast.LENGTH_SHORT).show()
                }
                else
                    opprettBruker(app, username, password)
            }
        }
        return binding.root
    }

    private fun gyldigPassord(passord: String) : Boolean {
        val tall = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val delim = ""
        val arr = passord.split(delim).toTypedArray()
        var godkjent: Boolean = false;

        for (i in 0 until arr.size) {
            for (j in 0 until tall.size) {
                if (arr[i] == tall[j])
                    godkjent = true;
            }
        }
        return godkjent
    }

    private fun opprettBruker(app: App, username: String, password: String) {
        // while this operation completes, disable the buttons to login or create a new account
        //createUserButton.isEnabled = false
        //loginButton.isEnabled = false
        var user: User? = app.currentUser()
        user = null
            // register a user using the Realm App we created in the TaskTracker class
            app.emailPassword.registerUserAsync(username, password) {
                // re-enable the buttons after user registration returns a result
                if (!it.isSuccess) {
                    onLoginFailed("KAN IKKE OPPRETTE BRUKER")
                    Log.e(ContentValues.TAG, "Error: ${it.error}")
                    if (it.error.errorCode.intValue() == 48)
                        Toast.makeText(context, "Passord må være mellom 6 og 128 karakterer", Toast.LENGTH_SHORT).show()

                } else {
                    Log.i(ContentValues.TAG, "OPPRETTET EN SKIKKELIG BRUKER!!")
                    //user = app.currentUser()
                    // when the account has been created successfully, log in to the account
                    view?.findNavController()?.navigate(R.id.action_opprettBrukerFragment_to_godkjentBruker)
                }
            }
    }

    /*
    private fun opprettBruker(createUser: Boolean, app: App, username: String, password: String) {
        // while this operation completes, disable the buttons to login or create a new account
        //createUserButton.isEnabled = false
        //loginButton.isEnabled = false
        var user: User? = null

        if (createUser) {
            // register a user using the Realm App we created in the TaskTracker class
            app.emailPassword.registerUserAsync(username, password) {
                // re-enable the buttons after user registration returns a result
                if (!it.isSuccess) {
                    onLoginFailed("KAN IKKE OPPRETTE BRUKER")
                    Log.e(ContentValues.TAG, "Error: ${it.error}")
                    if (it.error.errorCode.intValue() == 48)
                        Toast.makeText(context, "Passord må være mellom 6 og 128 karakterer", Toast.LENGTH_SHORT).show()

                } else {
                    Log.i(ContentValues.TAG, "OPPRETTET EN SKIKKELIG BRUKER!!")
                    user = app.currentUser()
                    // when the account has been created successfully, log in to the account
                    opprettBruker(false, app, username, password)
                    view?.findNavController()?.navigate(R.id.action_opprettBrukerFragment_to_godkjentBruker)
                }
            }
        }
        else {
            val creds = Credentials.emailPassword(username, password)
            app.loginAsync(creds) {
                // re-enable the buttons after user login returns a result
                if (!it.isSuccess) {
                    onLoginFailed(it.error.message ?: "An error occurred.")
                } else {
                    //onLoginSuccess()
                    Log.v(ContentValues.TAG, "KLARTE Å LOGGEGE INN SKIKKELIG!!")
                }
            }
        }
    }

     */

    private fun onLoginFailed(errorMsg: String) {
        Log.e(ContentValues.TAG, errorMsg)
    }

}