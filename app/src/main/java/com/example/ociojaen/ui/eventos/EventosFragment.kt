package com.example.ociojaen.ui.eventos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ociojaen.adapter.EventoAdapter
import com.example.ociojaen.data.models.Evento
import com.example.ociojaen.databinding.FragmentEventosBinding

class EventosFragment : Fragment() {

    private lateinit var binding: FragmentEventosBinding
    private val viewModel: EventosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración del RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = EventoAdapter(
            eventos = emptyList(), // Lista vacía inicialmente
            onEliminarClick = { evento -> viewModel.eliminarEvento(evento) },
            onEditarClick = { evento -> mostrarDialogoEditarEvento(evento) }
        )
        binding.recyclerView.adapter = adapter

        // Observamos los cambios en los eventos
        viewModel.eventos.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista) // Actualizamos la lista de eventos
        }

        // Acción del FloatingActionButton para añadir un nuevo evento
        binding.btnAdd.setOnClickListener {
            mostrarDialogoAgregarEvento()
        }
    }

    private fun mostrarDialogoAgregarEvento() {
        val dialogFragment = EventoDialogFragment { nuevoEvento ->
            // Llamamos al ViewModel para agregar el nuevo evento
            viewModel.agregarEvento(nuevoEvento)
        }
        dialogFragment.show(parentFragmentManager, "AgregarEventoDialog")
    }

    private fun mostrarDialogoEditarEvento(evento: Evento) {
        val dialogFragment = EventoDialogFragment(evento) { eventoEditado ->
            viewModel.editarEvento(evento, eventoEditado) // Pasar evento original y editado
        }
        dialogFragment.show(parentFragmentManager, "EditarEventoDialog")
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Eventos"
    }
}


