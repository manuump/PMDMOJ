package com.example.ociojaen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ociojaen.adapter.EventoAdapter
import com.example.ociojaen.models.Evento
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EventosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventoAdapter
    private val listaEventos = mutableListOf<Evento>() // Lista de eventos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eventos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()

        // Crear lista
        listaEventos.add(Evento(
            "Castillo de Santa Catalina",
            "Visita guiada al Castillo de Santa Catalina de Jaén, desde 3,5€.",
            "castillosantacatalina")
        )
        listaEventos.add(Evento(
            "Real Jaén CF",
            "Aqui podrás encontrar información de todos los partidos del Real Jaén.",
            "realjaen")
        )
        listaEventos.add(Evento(
            "Festival LaMonaFest",
            "II edidión del festival mas grande de la ciudad de Jaén , desde 35€.",
            "lamonafest")
        )
        listaEventos.add(Evento(
            "Jaen Plaza",
            "El centro comercial mas moderno y grande de la provincia de Jaén.",
            "jaenplaza")
        )
        listaEventos.add(Evento(
            "Expoliva",
            "La principal Feria Mundial para la promoción y el desarrollo del Sector del Olivar y el Aceite de Oliva.",
            "expoliva")
        )

        adapter = EventoAdapter(listaEventos,
            onEliminarClick = { evento ->
                eliminarEvento(evento) // Llamar a la función eliminar
            },
            onEditarClick = { evento ->
                mostrarDialogoEditarEvento(evento) // Llamar a la función editar
            }
        )

        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.btnAdd).setOnClickListener {
            mostrarDialogoAgregarEvento()
        }
    }

    private fun eliminarEvento(evento: Evento) {
        val position = listaEventos.indexOf(evento) // Obtener la posición del evento
        if (position != -1) { // Validar que el evento existe
            listaEventos.removeAt(position) // Eliminar el evento de la lista
            adapter.notifyItemRemoved(position) // Notificar al adaptador con animación
        }
    }

    private fun mostrarDialogoAgregarEvento() {
        val dialogFragment = EventoDialogFragment { nuevoEvento ->
            listaEventos.add(nuevoEvento) // Agregar a la lista
            adapter.notifyItemInserted(listaEventos.size - 1) // Notificar al adaptador
        }
        dialogFragment.show(supportFragmentManager, "AgregarEventoDialog")
    }

    private fun mostrarDialogoEditarEvento(evento: Evento) {
        val position = listaEventos.indexOf(evento)
        if (position != -1) {
            val dialogFragment = EventoDialogFragment(evento) { eventoEditado ->
                listaEventos[position] = eventoEditado // Actualizar el evento
                adapter.notifyItemChanged(position) // Notificar al adaptador
            }
            dialogFragment.show(supportFragmentManager, "EditarEventoDialog")
        }
    }
}