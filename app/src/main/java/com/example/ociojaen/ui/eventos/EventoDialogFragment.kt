package com.example.ociojaen.ui.eventos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ociojaen.data.models.Evento
import com.example.ociojaen.databinding.FragmentEventoDialogBinding
import com.google.android.material.snackbar.Snackbar

class EventoDialogFragment(
    private val evento: Evento? = null,
    private val onEventoGuardado: (Evento) -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentEventoDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Si estamos editando, precargar los datos del evento
        evento?.let {
            binding.etTitulo.setText(it.titulo)
            binding.etDescripcion.setText(it.descripcion)
        }

        binding.btnGuardar.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            if (titulo.isEmpty() || descripcion.isEmpty()) {
                mostrarSnackbar("Por favor, completa todos los campos")
                return@setOnClickListener
            }

            val eventoNuevo = Evento(
                id = evento?.id, // Si es una edición, mantiene el ID
                titulo = titulo,
                descripcion = descripcion,
                imagen = evento?.imagen ?: "" // Mantener la imagen existente o usar una vacía
            )

            onEventoGuardado(eventoNuevo)
            dismiss()
        }

        binding.btnCancelar.setOnClickListener {
            dismiss()
        }
    }

    private fun mostrarSnackbar(mensaje: String) {
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}