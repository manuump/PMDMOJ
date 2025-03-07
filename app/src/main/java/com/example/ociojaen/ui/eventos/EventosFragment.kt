package com.example.ociojaen.ui.eventos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ociojaen.adapter.EventoAdapter
import com.example.ociojaen.data.models.Evento
import com.example.ociojaen.databinding.FragmentEventosBinding
import com.google.android.material.snackbar.Snackbar

class EventosFragment : Fragment() {

    private lateinit var binding: FragmentEventosBinding
    private lateinit var viewModel: EventosViewModel
    private lateinit var adapter: EventoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventosViewModel::class.java)

        // Configurar RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EventoAdapter(emptyList(), onEliminarClick = { evento ->
            eliminarEvento(evento)
        }, onEditarClick = { evento ->
            editarEvento(evento)
        })
        recyclerView.adapter = adapter

        // Obtener el token de SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            viewModel.getEventos(token)
        } else {
            mostrarSnackbar("No se encontró el token de autenticación")
        }

        // Observar cambios en la lista de eventos
        viewModel.eventos.observe(viewLifecycleOwner) { eventos ->
            eventos?.let {
                adapter.actualizarLista(it)
            }
        }

        // Observar mensajes de error
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            mostrarSnackbar(errorMessage)
        }

        // Configurar el botón flotante (FAB)
        binding.btnAdd.setOnClickListener {
            abrirDialogoNuevoEvento(token)
        }
    }

    private fun abrirDialogoNuevoEvento(token: String?) {
        if (token == null) {
            mostrarSnackbar("No se encontró el token de autenticación")
            return
        }

        // Crear un diálogo para añadir un nuevo evento
        val dialog = EventoDialogFragment(onEventoGuardado = { nuevoEvento ->
            viewModel.createEvento(token, nuevoEvento, onSuccess = {
                mostrarSnackbar("Evento creado con éxito")
                viewModel.getEventos(token)
            }, onError = {
                mostrarSnackbar("Error al crear el evento")
            })
        })
        dialog.show(parentFragmentManager, "EventoDialogFragment")
    }

    private fun eliminarEvento(evento: Evento) {
        val sharedPreferences = requireActivity().getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            viewModel.deleteEvento(token, evento.id!!, onSuccess = {
                mostrarSnackbar("Evento eliminado")
                viewModel.getEventos(token)
            }, onError = {
                mostrarSnackbar("Error al eliminar el evento")
            })
        }
    }

    private fun editarEvento(evento: Evento) {
        val sharedPreferences = requireActivity().getSharedPreferences("PreferenciasApp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token == null) {
            mostrarSnackbar("No se encontró el token de autenticación")
            return
        }

        // Crear un diálogo para editar el evento
        val dialog = EventoDialogFragment(evento, onEventoGuardado = { eventoActualizado ->
            viewModel.updateEvento(token, evento.id!!, eventoActualizado, onSuccess = {
                mostrarSnackbar("Evento actualizado")
                viewModel.getEventos(token)
            }, onError = {
                mostrarSnackbar("Error al actualizar el evento")
            })
        })
        dialog.show(parentFragmentManager, "EventoDialogFragment")
    }

    private fun mostrarSnackbar(mensaje: String) {
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}