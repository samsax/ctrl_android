package mx.ctrlpg.ui.login

import ResponseLogin
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import mx.ctrlpg.ApiUtils.apiService
import mx.ctrlpg.MainActivity
import mx.ctrlpg.R
import mx.ctrlpg.util.PreferenceHelper
import mx.ctrlpg.util.UtilOwner
import mx.ctrlpg.util.VariableConstants.AUTHORIZATION
import mx.ctrlpg.util.VariableConstants.USUARIONOMBRECOMPLETO
import mx.ctrlpg.util.VariableConstants.USUARIOSESION
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(PreferenceHelper.read(AUTHORIZATION,"")!=""){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginUser(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginUser(
                    username.text.toString(),
                    password.text.toString()
                )

            }
        }
    }

    private fun loginUser(userName: String, pass: String){

        val call = apiService.login(userName,
            UtilOwner().toMD5(pass)
        )

        call.enqueue(object : Callback<ResponseLogin> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val loginResponse = response.body()

                if (!loginResponse?.error!!) {
                    Toast.makeText(this@LoginActivity,loginResponse.info.message,Toast.LENGTH_LONG).show()
                    loginResponse.info.message
//                    SharedPrefManager.getInstance(this@LoginActivity)
//                        .saveUser(loginResponse.getUser())
                    PreferenceHelper.write(AUTHORIZATION,loginResponse.info.objectClass.autorization)
                    PreferenceHelper.write(USUARIONOMBRECOMPLETO,loginResponse.info.objectClass.usuarioNombreCompleto)
                    PreferenceHelper.write(USUARIOSESION,loginResponse.info.objectClass.usuarioSesion)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)


                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        loginResponse.info.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


