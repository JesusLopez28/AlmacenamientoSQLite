package com.example.almacenamientosqlite

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ParquesFragment : Fragment() {

    private lateinit var dbHelper: ParqueDatabaseHelper
    private lateinit var nombreEditText: EditText
    private lateinit var ubicacionEditText: EditText
    private lateinit var tipoSpinner: Spinner
    private lateinit var tamanoEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var buscarButton: Button
    private lateinit var editarButton: Button
    private lateinit var eliminarButton: Button
    private lateinit var btnLimpiar: Button
    private var currentParqueId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parques, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = ParqueDatabaseHelper(requireContext())

        // Enlazar los elementos de UI
        nombreEditText = view.findViewById(R.id.editTextParqueNombre)
        ubicacionEditText = view.findViewById(R.id.editTextParqueUbicacion)
        tipoSpinner = view.findViewById(R.id.spinnerTipoParque)
        tamanoEditText = view.findViewById(R.id.editTextParqueTamano)
        descripcionEditText = view.findViewById(R.id.editTextParqueDescripcion)
        registrarButton = view.findViewById(R.id.btnRegistrarParque)
        buscarButton = view.findViewById(R.id.btnBuscarParque)
        editarButton = view.findViewById(R.id.btnEditarParque)
        eliminarButton = view.findViewById(R.id.btnEliminarParque)
        btnLimpiar = view.findViewById(R.id.btnLimpiar)

        // Configurar los eventos de clic para cada botón
        registrarButton.setOnClickListener { registrarParque() }
        buscarButton.setOnClickListener { buscarParque() }
        editarButton.setOnClickListener { editarParque() }
        eliminarButton.setOnClickListener { eliminarParque() }
        btnLimpiar.setOnClickListener { limpiarCampos() }
    }

    private fun registrarParque() {
        val nombre = nombreEditText.text.toString()
        val ubicacion = ubicacionEditText.text.toString()
        val tipo = tipoSpinner.selectedItem.toString()
        val tamano = tamanoEditText.text.toString().toIntOrNull() ?: 0
        val descripcion = descripcionEditText.text.toString()

        val result = dbHelper.insertarParque(nombre, ubicacion, tipo, tamano, descripcion)
        if (result != -1L) {
            Toast.makeText(context, "Parque registrado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al registrar el parque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarParque() {
        val nombre = nombreEditText.text.toString()
        val cursor: Cursor? = dbHelper.buscarParque(nombre)

        if (cursor != null && cursor.moveToFirst()) {
            currentParqueId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            ubicacionEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")))
            tipoSpinner.setSelection(getTipoIndex(cursor.getString(cursor.getColumnIndexOrThrow("tipo"))))
            tamanoEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow("tamano")).toString())
            descripcionEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")))
            Toast.makeText(context, "Parque encontrado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Parque no encontrado", Toast.LENGTH_SHORT).show()
        }
        cursor?.close()
    }

    private fun editarParque() {
        if (currentParqueId == -1) {
            Toast.makeText(context, "Busca un parque primero", Toast.LENGTH_SHORT).show()
            return
        }

        val nombre = nombreEditText.text.toString()
        val ubicacion = ubicacionEditText.text.toString()
        val tipo = tipoSpinner.selectedItem.toString()
        val tamano = tamanoEditText.text.toString().toIntOrNull() ?: 0
        val descripcion = descripcionEditText.text.toString()

        val rowsAffected =
            dbHelper.actualizarParque(currentParqueId, nombre, ubicacion, tipo, tamano, descripcion)
        if (rowsAffected > 0) {
            Toast.makeText(context, "Parque actualizado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarParque() {
        if (currentParqueId == -1) {
            Toast.makeText(context, "Busca un parque primero", Toast.LENGTH_SHORT).show()
            return
        }

        val rowsDeleted = dbHelper.eliminarParque(currentParqueId)
        if (rowsDeleted > 0) {
            Toast.makeText(context, "Parque eliminado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        nombreEditText.text.clear()
        ubicacionEditText.text.clear()
        tipoSpinner.setSelection(0)
        tamanoEditText.text.clear()
        descripcionEditText.text.clear()
        currentParqueId = -1
    }

    private fun getTipoIndex(tipo: String): Int {
        val tipos = resources.getStringArray(R.array.tipos_parque)
        return tipos.indexOf(tipo)
    }
}
