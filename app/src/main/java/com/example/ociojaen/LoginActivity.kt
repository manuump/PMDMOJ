package com.example.ociojaen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Verificar si el usuario ya está logueado
        sharedPreferences = getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Redirigir al principal si ya está logueado
            val intent = Intent(this, EventosActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Inicializar firebase
        auth = Firebase.auth

        // Referencia a los EditText y botones
        val emailEditText = findViewById<EditText>(R.id.usuarioLogin)
        val passEditText = findViewById<EditText>(R.id.passwordLogin)
        val botonAcceso = findViewById<Button>(R.id.botonAcceso)
        val botonRegistro = findViewById<Button>(R.id.botonRegistro)
        val botonRecuperar = findViewById<Button>(R.id.botonRecuperar)


        // Manejar el evento de clic en el botón de acceso
        botonAcceso.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                iniciarSesion(email, password)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Manejar el evento de clic en el botón de registro
        botonRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        // Manejar el evento de clic en el botón de recuperación
        botonRecuperar.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            if (email.isNotEmpty()) {
                recuperarContrasena(email)
            } else {
                Toast.makeText(this, "Ingrese su correo para recuperar la contraseña", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Método para iniciar sesión
    private fun iniciarSesion(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        // Guardar el estado de inicio de sesión en SharedPreferences
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, EventosActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Verifique su correo electrónico", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Email o contraseñas incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Método para recuperar contraseña
    private fun recuperarContrasena(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}