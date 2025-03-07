package com.example.ociojaen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ociojaen.ui.auth.AuthViewModel

class RegistroActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val usernameEditText = findViewById<EditText>(R.id.usuarioRegistro)
        val passwordEditText = findViewById<EditText>(R.id.passwordRegistro)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordRegistro)
        val registerButton = findViewById<Button>(R.id.botonCompletarRegistro)
        val atras = findViewById<Button>(R.id.botonVolver)

        atras.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (password == confirmPassword) {
                viewModel.register(username, password).observe(this) { response ->
                    if (response != null) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
