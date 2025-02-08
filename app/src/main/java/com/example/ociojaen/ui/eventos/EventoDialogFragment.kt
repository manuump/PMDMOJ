package com.example.ociojaen.ui.eventos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.ociojaen.R
import com.example.ociojaen.data.models.Evento

class EventoDialogFragment(private val evento: Evento? = null, private val onEventoGuardado: (Evento) -> Unit) : DialogFragment() {

    private lateinit var etTitulo: EditText
    private lateinit var etDescripcion: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este diálogo
        return inflater.inflate(R.layout.fragment_evento_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar las vistas usando findViewById
        etTitulo = view.findViewById(R.id.etTitulo)
        etDescripcion = view.findViewById(R.id.etDescripcion)

        // Si estamos editando, precargamos los datos del evento
        evento?.let {
            etTitulo.setText(it.titulo)
            etDescripcion.setText(it.descripcion)
        }

        // Acción del botón "Guardar"
        view.findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()

            val eventoGuardado = Evento(titulo, descripcion, "") // Asignamos una cadena vacía para la imagen

            // Llamamos al callback para devolver el evento creado o editado
            onEventoGuardado(eventoGuardado)

            dismiss() // Cerrar el diálogo
        }

        // Acción del botón "Cancelar"
        view.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            dismiss() // Cerrar el diálogo sin hacer nada
        }
    }
}

