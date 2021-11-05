package usn.gruppe7.eskapader_android

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import usn.gruppe7.eskapader_android.databinding.ActivityLoginBinding
lateinit var app: App
class LoginActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var gjestLoginButton: Button
    private lateinit var createUserButton: TextView



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Realm.init(this)
        app = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .defaultSyncErrorHandler { session, error ->
                    Log.e(ContentValues.TAG, "Sync error: ${error.errorMessage}")
                }
                .build())

        // Enable more logging in debug mode
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        Log.v(ContentValues.TAG, "Initialized the Realm App configuration for: ${app.configuration.appId}")
        val volley = VolleyObjekt(this)
        volley.vekkAPI()

        username = findViewById(R.id.passordInput)
        password = findViewById(R.id.brukernavnInput)
        loginButton = findViewById(R.id.loggInnBtn)
        gjestLoginButton = findViewById(R.id.gjestLoggInnbt)
        createUserButton = findViewById(R.id.textView2)

        gjestLoginButton.setOnClickListener { view : View ->
            loggInnGjest(app);
            view.findNavController().navigate(R.id.action_loggInnFragment_to_hovedMenyActivity)
        };

        createUserButton.setOnClickListener{view: View ->
            view?.findNavController()?.navigate(R.id.action_loggInnFragment_to_opprettBrukerFragment2)
        }

        loginButton.setOnClickListener { view: View ->
            Log.d("Debug", "Trykket på LOGIN")
            if (username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
                loggInn(app, username.text.toString(), password.text.toString(), view)
            }
            else
                Log.v("X", "BRUKERNAVN ELLER PASSORD ER TOM!!!!")
        }



    }

    private fun loggInnGjest(app: App) {
        val credentials: Credentials = Credentials.anonymous()
        var user: User? = null
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                Log.d(ContentValues.TAG, "Successfully authenticated anonymously.")
                user = app.currentUser()
            } else {
                Log.e(ContentValues.TAG, it.error.toString())
            }
        }

    }

    private fun loggInn(app: App, username: String, password: String, view: View) {
        val creds = Credentials.emailPassword(username, password)

        var user = app.currentUser()
        /*
        if (user != null)
            Log.d(ContentValues.TAG, "Det finnes en bruker!")

         */

        app.loginAsync(creds) {
            // re-enable the buttons after user login returns a result
            if (!it.isSuccess) {
                onLoginFailed(it.error.message ?: "FEIL I INNLOGGING")
            } else {
                //onLoginSuccess()
                view.findNavController().navigate(R.id.action_loggInnFragment_to_hovedMenyActivity)
                Log.v(ContentValues.TAG, "KLARTE Å LOGGEGE INN SKIKKELIG!!")
            }
        }
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e(ContentValues.TAG, errorMsg)
    }


    /*
    private fun onLoginSuccess() {
        // successful login ends this activity, bringing the user back to the project activity
        finish()
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e(TAG(), errorMsg)
        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_LONG).show()
    }

    private fun validateCredentials(): Boolean = when {
        // zero-length usernames and passwords are not valid (or secure), so prevent users from creating accounts with those client-side.
        username.text.toString().isEmpty() -> false
        password.text.toString().isEmpty() -> false
        else -> true
    }

    private fun loginGjest() {
        val credentials: Credentials = Credentials.anonymous()
        var user: User? = null
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                Log.v(ContentValues.TAG, "Successfully authenticated anonymously.")
                user = app.currentUser()
            } else {
                Log.e(ContentValues.TAG, it.error.toString())
            }
        }
    }

    // handle user authentication (login) and account creation
    private fun login(createUser: Boolean) {
        if (!validateCredentials()) {
            onLoginFailed("Invalid username or password")
            return
        }

        // while this operation completes, disable the buttons to login or create a new account
        createUserButton.isEnabled = false
        loginButton.isEnabled = false

        val username = this.username.text.toString()
        val password = this.password.text.toString()


        if (createUser) {
            // register a user using the Realm App we created in the TaskTracker class
            app.emailPassword.registerUserAsync(username, password) {
                // re-enable the buttons after user registration returns a result
                createUserButton.isEnabled = true
                loginButton.isEnabled = true
                if (!it.isSuccess) {
                    onLoginFailed("Could not register user.")
                    Log.e(TAG(), "Error: ${it.error}")
                } else {
                    Log.i(TAG(), "Successfully registered user.")
                    // when the account has been created successfully, log in to the account
                    login(false)
                }
            }
        } else {
            val creds = Credentials.emailPassword(username, password)
            app.loginAsync(creds) {
                // re-enable the buttons after user login returns a result
                loginButton.isEnabled = true
                createUserButton.isEnabled = true
                if (!it.isSuccess) {
                    onLoginFailed(it.error.message ?: "An error occurred.")
                } else {
                    onLoginSuccess()
                }
            }
        }

     */

}