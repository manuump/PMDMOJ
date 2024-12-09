package com.example.ociojaen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.ociojaen.models.Evento

class EventoDialogFragment(
    private val evento: Evento? = null,
    private val onEventoGuardado: (Evento) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_evento_dialog, null)
        val tituloInput = view.findViewById<EditText>(R.id.etTitulo)
        val descripcionInput = view.findViewById<EditText>(R.id.etDescripcion)

        // Precargar datos si es edición
        evento?.let {
            tituloInput.setText(it.titulo)
            descripcionInput.setText(it.descripcion)
        }

        builder.setView(view)
            .setTitle(if (evento == null) "Agregar Evento" else "Editar Evento")
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoEvento = Evento(
                    tituloInput.text.toString(),
                    descripcionInput.text.toString(),
                    evento?.imagen ?: "default_image"
                )
                onEventoGuardado(nuevoEvento)
            }
            .setNegativeButton("Cancelar", null)

        return builder.create()
    }
}
