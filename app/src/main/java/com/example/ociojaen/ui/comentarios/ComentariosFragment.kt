package com.example.ociojaen.ui.comentarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ociojaen.adapter.ComentarioAdapter
import com.example.ociojaen.databinding.FragmentComentariosBinding


class ComentariosFragment : Fragment() {

    private lateinit var binding: FragmentComentariosBinding
    private val viewModel: ComentariosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentComentariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ComentarioAdapter()
        binding.recyclerView.adapter = adapter

        // Observar los comentarios
        viewModel.comentarios.observe(viewLifecycleOwner) { listaComentarios ->
            adapter.submitList(listaComentarios)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Comentarios"
    }
}

