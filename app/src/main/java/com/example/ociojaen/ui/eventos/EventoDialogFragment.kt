package com.example.ociojaen.ui.eventos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.example.ociojaen.R
import com.example.ociojaen.data.models.Evento
import java.io.*

class EventoDialogFragment(
    private val evento: Evento? = null, // Si es null, es un nuevo evento
    private val onEventoGuardado: (Evento) -> Unit
) : DialogFragment() {

    private lateinit var etTitulo: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var ivPreview: ImageView
    private lateinit var btnTomarFoto: Button
    private lateinit var btnSeleccionarGaleria: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private var currentPhotoPath: String? = null
    private var imagenBase64: String? = null // Para almacenar la imagen en Base64
    private var imagenUri: String? = null // Guardará la URI de la imagen tomada

    private val REQUEST_CAMERA = 100
    private val REQUEST_GALLERY = 101
    private val PERMISSION_CAMERA = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_evento_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTitulo = view.findViewById(R.id.etTitulo)
        etDescripcion = view.findViewById(R.id.etDescripcion)
        ivPreview = view.findViewById(R.id.ivPreview)
        btnTomarFoto = view.findViewById(R.id.btnTomarFoto)
        btnSeleccionarGaleria = view.findViewById(R.id.btnSeleccionarGaleria)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        // Si estamos editando, precargar los datos
        evento?.let {
            etTitulo.setText(it.titulo)
            etDescripcion.setText(it.descripcion)

            // Si hay una imagen previa, mostrarla
            if (it.imagen.isNotEmpty()) {
                val bitmap = BitmapFactory.decodeFile(it.imagen)
                ivPreview.setImageBitmap(bitmap)
                imagenUri = it.imagen // Guardar la URI de la imagen existente
            }
        }

        btnTomarFoto.setOnClickListener { tomarFoto() }
        btnSeleccionarGaleria.setOnClickListener { seleccionarImagenGaleria() }

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()

            val eventoGuardado = Evento(
                titulo = titulo,
                descripcion = descripcion,
                imagen = imagenUri ?: evento?.imagen ?: "" // Guardamos la imagen URI
            )

            onEventoGuardado(eventoGuardado)
            dismiss()
        }

        btnCancelar.setOnClickListener { dismiss() }
    }

    private fun tomarFoto() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no tiene permisos, pedirlos en tiempo de ejecución
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
        } else {
            // Crear archivo para guardar la imagen
            val photoFile = createImageFile()
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.ociojaen.fileprovider",
                    photoFile
                )

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                // Verificar que hay una app de cámara disponible
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivityForResult(intent, REQUEST_CAMERA)
                } else {
                    Toast.makeText(requireContext(), "No se encontró una app de cámara", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Error al crear el archivo de imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun seleccionarImagenGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun createImageFile(): File? {
        return try {
            val storageDir = File(requireContext().getExternalFilesDir(null), "Pictures")
            if (!storageDir.exists()) {
                storageDir.mkdirs() // Crea la carpeta si no existe
            }

            val file = File(storageDir, "evento_${System.currentTimeMillis()}.jpg")
            currentPhotoPath = file.absolutePath
            file
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    if (bitmap != null) {
                        ivPreview.setImageBitmap(bitmap)
                        imagenUri = currentPhotoPath // Guardamos la URI
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
                    }
                }
                REQUEST_GALLERY -> {
                    val selectedImageUri = data?.data ?: return
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                    ivPreview.setImageBitmap(bitmap)
                    imagenUri = guardarImagen(bitmap) // Guardamos la imagen seleccionada
                }
            }
        }
    }


    private fun guardarImagen(bitmap: Bitmap): String {
        val file = File(requireContext().filesDir, "evento_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return file.absolutePath // Retornamos la ruta donde se guardó la imagen
    }

    private fun convertirImagenABase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }
}
