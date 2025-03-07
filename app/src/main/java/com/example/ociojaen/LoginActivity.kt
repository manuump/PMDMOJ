package com.example.ociojaen
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.example.ociojaen.ui.auth.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)

        val usernameEditText = findViewById<EditText>(R.id.usuarioLogin)
        val passEditText = findViewById<EditText>(R.id.passwordLogin)
        val botonAcceso = findViewById<Button>(R.id.botonAcceso)
        val botonRegistro = findViewById<Button>(R.id.botonRegistro)

        botonRegistro.setOnClickListener{
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        botonAcceso.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passEditText.text.toString().trim()

            viewModel.login(username, password).observe(this) { response ->
                if (response != null && response.token.isNotEmpty()) {
                    sharedPreferences.edit().apply {
                        putBoolean("isLoggedIn", true)
                        putString("token", response.token)
                        apply()
                    }
                    startActivity(Intent(this, EventosActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error de login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
