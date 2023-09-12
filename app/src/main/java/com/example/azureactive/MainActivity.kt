package com.example.azureactive


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.IPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.PublicClientApplication


class MainActivity : AppCompatActivity() {

    private var msalApplication: ISingleAccountPublicClientApplication? = null
    private var account: IAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logInButton: Button = findViewById(R.id.logInButton)
        logInButton.setOnClickListener {
            logIn()
        }
        logInButton.isEnabled = false



        try {
            PublicClientApplication.createSingleAccountPublicClientApplication(
                this,
                R.raw.auth_config_b2c,
                object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {

                    override fun onCreated(application: ISingleAccountPublicClientApplication?) {
                        println("heeeeeeeeeeeeeeeeeeeeeeeee")
                        msalApplication = application
                        logInButton.isEnabled = true
                    }

                    override fun onError(exception: MsalException?) {
                        print(exception!!)
                    }
                })
        } catch (error: Error) {
            println(error)
        }
    }


    private fun logIn() {
        val scopes =
            arrayOf("https://icaniocom.onmicrosoft.com/api/api.read")
        msalApplication!!.signIn(
            this,
            null,
            scopes,
            object : AuthenticationCallback {
                override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                    account = authenticationResult!!.account
                }

                override fun onError(exception: MsalException?) {
                    print(exception!!)
                }

                override fun onCancel() {
                    print("Cancelled")
                }
            })
    }
}


