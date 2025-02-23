package com.example.ociojaen.ui.eventos

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EventoDialogFragment(
    private val evento: Evento? = null, // Evento a editar, si es null significa que es nuevo
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
    private var imagenBase64: String? = null // Imagen en Base64 para futuro uso

    private val REQUEST_CAMERA = 100
    private val REQUEST_GALLERY = 101
    private val PERMISSION_CAMERA = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_evento_dialog, container, false)

        etTitulo = view.findViewById(R.id.etTitulo)
        etDescripcion = view.findViewById(R.id.etDescripcion)
        ivPreview = view.findViewById(R.id.ivPreview)
        btnTomarFoto = view.findViewById(R.id.btnTomarFoto)
        btnSeleccionarGaleria = view.findViewById(R.id.btnSeleccionarGaleria)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        // Si es edición, rellenar los campos
        evento?.let {
            etTitulo.setText(it.titulo)
            etDescripcion.setText(it.descripcion)
        }

        btnTomarFoto.setOnClickListener { tomarFoto() }
        btnSeleccionarGaleria.setOnClickListener { seleccionarImagenGaleria() }

        btnGuardar.setOnClickListener {
            val nuevoEvento = Evento(
                titulo = etTitulo.text.toString(),
                descripcion = etDescripcion.text.toString(),
                imagen = imagenBase64 ?: evento?.imagen ?: "" // Mantener imagen si no se cambia
            )
            onEventoGuardado(nuevoEvento)
            dismiss()
        }

        btnCancelar.setOnClickListener { dismiss() }

        return view
    }

    private fun tomarFoto() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile = createImageFile()
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(requireContext(), "com.example.ociojaen.fileprovider", it)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_CAMERA)
            }
        }
    }

    private fun seleccionarImagenGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun createImageFile(): File? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir: File? = requireContext().getExternalFilesDir(null)
            File.createTempFile("IMG_${timeStamp}_", ".jpg", storageDir).apply {
                currentPhotoPath = absolutePath
            }
        } catch (ex: IOException) {
            null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    ivPreview.setImageBitmap(bitmap)
                    imagenBase64 = convertirImagenABase64(bitmap)
                }
                REQUEST_GALLERY -> {
                    val selectedImageUri = data?.data ?: return
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                    ivPreview.setImageBitmap(bitmap)
                    imagenBase64 = convertirImagenABase64(bitmap)
                }
            }
        }
    }

    private fun convertirImagenABase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }
}
