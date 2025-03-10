package com.example.ociojaen.ui.eventos

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.ociojaen.data.models.Evento
import com.example.ociojaen.databinding.FragmentEventoDialogBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.InputStream

class EventoDialogFragment(
    private val evento: Evento? = null,
    private val onEventoGuardado: (Evento) -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentEventoDialogBinding
    private var imagenBase64: String? = null  // Variable para almacenar la imagen en Base64

    private lateinit var seleccionarImagenLauncher: ActivityResultLauncher<Intent>

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
            if (!it.imagen.isNullOrEmpty()) {
                imagenBase64 = it.imagen
                binding.ivPreview.setImageBitmap(decodeBase64ToBitmap(it.imagen))
            }
        }

        // Configurar el launcher para seleccionar imágenes de la galería
        seleccionarImagenLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    convertirImagenABase64(uri)
                }
            }
        }

        // Botón para seleccionar imagen de la galería
        binding.btnSeleccionarGaleria.setOnClickListener {
            abrirGaleria()
        }

        binding.btnGuardar.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            if (titulo.isEmpty() || descripcion.isEmpty()) {
                mostrarSnackbar("Por favor, completa todos los campos")
                return@setOnClickListener
            }

            val eventoNuevo = Evento(
                id = evento?.id, // Mantener el ID si es edición
                titulo = titulo,
                descripcion = descripcion,
                imagen = imagenBase64 ?: evento?.imagen ?: "" // Mantener imagen existente o usar nueva
            )

            onEventoGuardado(eventoNuevo)
            dismiss()
        }

        binding.btnCancelar.setOnClickListener {
            dismiss()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        seleccionarImagenLauncher.launch(intent)
    }

    private fun convertirImagenABase64(uri: Uri) {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.ivPreview.setImageBitmap(bitmap) // Mostrar la imagen en el ImageView

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        imagenBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun decodeBase64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun mostrarSnackbar(mensaje: String) {
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}
