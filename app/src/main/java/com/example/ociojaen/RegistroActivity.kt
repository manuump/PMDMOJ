package com.example.ociojaen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar firebase
        auth = Firebase.auth

        // Referencias a los elementos de la vista
        val emailEditText = findViewById<EditText>(R.id.usuarioRegistro)
        val passwordEditText = findViewById<EditText>(R.id.passwordRegistro)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordRegistro)
        val registerButton = findViewById<Button>(R.id.botonCompletarRegistro)
        val backButton = findViewById<Button>(R.id.botonVolver)

        // Manejar el botón de registro
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (validarDatos(email, password, confirmPassword)) {
                registroUsuario (email, password){
                        result, msg ->
                    Toast.makeText(this@RegistroActivity, msg, Toast.LENGTH_LONG).show()
                    if (result)
                        startActivityLogin()
                }
            }
        }

        // Manejar el botón de volver
        backButton.setOnClickListener {
            // Cierra esta actividad y regresa a la anterior
            finish()
        }

    }

    private fun startActivityLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }


    // Funcion que valida si los campos introducidos por el usuario son adecuados
    private fun validarDatos(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, introduce un correo válido.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    private fun registroUsuario(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this){
                    taskAssin->
                if (taskAssin.isSuccessful){
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener{
                                taskVerification ->
                            var msg = ""
                            if (taskVerification.isSuccessful)
                                msg = "Usuario Registrado con éxito. Verifique su correo"
                            else
                                msg = "Usuario Registrado. ${taskVerification.exception?.message}"
                            auth.signOut()
                            onResult(true, msg)
                        }
                        ?.addOnFailureListener{
                                exception->
                            Log.e("RegistroActivity", "Fallo al enviar correo de verificación: ${exception.message}")
                            onResult(false, "No se pudo enviar el correo de verificación: ${exception.message}")
                        }

                }else{
                    try{
                        throw taskAssin.exception ?:Exception ("Error desconocido")
                    } catch (e: FirebaseAuthUserCollisionException){
                        onResult (false, "Ese usuario ya existe")
                    }catch (e: FirebaseAuthWeakPasswordException){
                        onResult (false, "La contraseña es débil: ${e.reason}")
                    }
                    catch (e: FirebaseAuthInvalidCredentialsException){
                        onResult (false, "El email proporcionado, no es válido")
                    }
                    catch (e: Exception){
                        onResult (false, e.message.toString())
                    }

                }
            }


    }


}