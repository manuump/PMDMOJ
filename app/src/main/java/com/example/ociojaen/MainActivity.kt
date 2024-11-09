package com.example.ociojaen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val MYUSER = "user"
    val MYPASS = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencia a los EditText y el botón
        val userEditText = findViewById<EditText>(R.id.usuarioLogin)
        val passEditText = findViewById<EditText>(R.id.passwordLogin)
        val botonAcceso = findViewById<Button>(R.id.botonAcceso)

        // Configura el setOnClickListener del botón
        botonAcceso.setOnClickListener {
            val user = userEditText.text.toString()
            val pass = passEditText.text.toString()

            // Verifica si los datos coinciden
            if (user == MYUSER && pass == MYPASS) {
                // Si los datos son correctos, pasa a otro Activity
                val intent = Intent(this, EventosActivity::class.java).apply{
                    // Le paso al PrincipalActivity el usuario introducido
                    putExtra("EXTRA_USER", user)
                }
                startActivity(intent)
                // Finaliza para que el usuario no pueda volver a este Activity
                finish()
            } else {
                // Si los datos no son correctos, muestra un Toast
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}