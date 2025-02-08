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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class EventosActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        // Inicializar firebase
        auth = Firebase.auth
        sharedPreferences = getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)


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
        val user = auth.currentUser
        val headerView: View = navigationView.getHeaderView(0)
        val correoTextView = headerView.findViewById<TextView>(R.id.correoElectronico)

        if (user != null) {
            correoTextView.text = user.email
            correoTextView.visibility = View.VISIBLE
        } else {
            correoTextView.text = "No autenticado"
        }
    }

    private fun cerrarSesion() {
        auth.signOut()

        // Eliminar el estado de inicio de sesión de SharedPreferences
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

        // Redirigir al LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
