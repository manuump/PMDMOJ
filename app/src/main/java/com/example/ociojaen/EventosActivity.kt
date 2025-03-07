package com.example.ociojaen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ociojaen.ui.comentarios.ComentariosFragment
import com.example.ociojaen.ui.eventos.EventosFragment
import com.google.android.material.navigation.NavigationView

class EventosActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        // Obtener SharedPreferences
        sharedPreferences = getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)

        // Obtener datos del usuario desde SharedPreferences
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        userEmail = sharedPreferences.getString("user_email", "Usuario")

        if (!isLoggedIn) {
            // Si no está logueado, redirigir al Login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Configurar el DrawerLayout y Toolbar
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar NavigationView
        navigationView = findViewById(R.id.navigation_view)

        // Mostrar email en el Header del Navigation Drawer
        actualizarCorreoUsuario()

        // Configurar la navegación
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_eventos -> cambiarFragmento(EventosFragment())
                R.id.nav_comentarios -> cambiarFragmento(ComentariosFragment())
                R.id.nav_logout -> cerrarSesion()
            }
            drawerLayout.closeDrawers()
            true
        }

        // Mostrar EventosFragment por defecto
        if (savedInstanceState == null) {
            cambiarFragmento(EventosFragment())
            navigationView.setCheckedItem(R.id.nav_eventos)
        }
    }

    private fun cambiarFragmento(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, fragment)
            .commit()
    }

    private fun actualizarCorreoUsuario() {
        val headerView: View = navigationView.getHeaderView(0)
        val correoTextView = headerView.findViewById<TextView>(R.id.correoElectronico)

        correoTextView.text = userEmail ?: "No autenticado"
        correoTextView.visibility = View.VISIBLE
    }

    private fun cerrarSesion() {
        // Eliminar el estado de inicio de sesión y token de SharedPreferences
        sharedPreferences.edit().clear().apply()

        // Redirigir al LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
