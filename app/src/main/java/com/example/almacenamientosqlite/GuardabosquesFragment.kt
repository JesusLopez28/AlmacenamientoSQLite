package com.example.almacenamientosqlite

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class GuardabosquesFragment : Fragment() {

    private lateinit var dbHelper: GuardabosqueDatabaseHelper
    private lateinit var nombreEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var aniosServicioEditText: EditText
    private lateinit var especialidadEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var buscarButton: Button
    private lateinit var editarButton: Button
    private lateinit var eliminarButton: Button
    private lateinit var  btnLimpiar: Button
    private var currentGuardabosqueId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guardabosques, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = GuardabosqueDatabaseHelper(requireContext())

        // Enlazar los elementos de UI
        nombreEditText = view.findViewById(R.id.editTextGuardabosqueNombre)
        edadEditText = view.findViewById(R.id.editTextGuardabosqueEdad)
        aniosServicioEditText = view.findViewById(R.id.editTextGuardabosqueAniosServicio)
        especialidadEditText = view.findViewById(R.id.editTextGuardabosqueEspecialidad)
        descripcionEditText = view.findViewById(R.id.editTextGuardabosqueDescripcion)
        registrarButton = view.findViewById(R.id.btnRegistrarGuardabosque)
        buscarButton = view.findViewById(R.id.btnBuscarGuardabosque)
        editarButton = view.findViewById(R.id.btnEditarGuardabosque)
        eliminarButton = view.findViewById(R.id.btnEliminarGuardabosque)
        btnLimpiar = view.findViewById(R.id.btnLimpiar)

        // Configurar los eventos de clic para cada botón
        registrarButton.setOnClickListener { registrarGuardabosque() }
        buscarButton.setOnClickListener { buscarGuardabosque() }
        editarButton.setOnClickListener { editarGuardabosque() }
        eliminarButton.setOnClickListener { eliminarGuardabosque() }
        btnLimpiar.setOnClickListener { limpiarCampos() }
    }

    private fun registrarGuardabosque() {
        val nombre = nombreEditText.text.toString()
        val edad = edadEditText.text.toString().toIntOrNull() ?: 0
        val aniosServicio = aniosServicioEditText.text.toString().toIntOrNull() ?: 0
        val especialidad = especialidadEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()

        val result =
            dbHelper.insertarGuardabosque(nombre, edad, aniosServicio, especialidad, descripcion)
        if (result != -1L) {
            Toast.makeText(context, "Guardabosque registrado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al registrar el guardabosque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarGuardabosque() {
        val nombre = nombreEditText.text.toString()
        val cursor: Cursor? = dbHelper.buscarGuardabosque(nombre)

        if (cursor != null && cursor.moveToFirst()) {
            currentGuardabosqueId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            edadEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow("edad")).toString())
            aniosServicioEditText.setText(
                cursor.getInt(cursor.getColumnIndexOrThrow("anios_servicio")).toString()
            )
            especialidadEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("especialidad")))
            descripcionEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")))
            Toast.makeText(context, "Guardabosque encontrado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Guardabosque no encontrado", Toast.LENGTH_SHORT).show()
        }
        cursor?.close()
    }

    private fun editarGuardabosque() {
        if (currentGuardabosqueId == -1) {
            Toast.makeText(context, "Busca un guardabosque primero", Toast.LENGTH_SHORT).show()
            return
        }

        val nombre = nombreEditText.text.toString()
        val edad = edadEditText.text.toString().toIntOrNull() ?: 0
        val aniosServicio = aniosServicioEditText.text.toString().toIntOrNull() ?: 0
        val especialidad = especialidadEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()

        val rowsAffected = dbHelper.actualizarGuardabosque(
            currentGuardabosqueId,
            nombre,
            edad,
            aniosServicio,
            especialidad,
            descripcion
        )
        if (rowsAffected > 0) {
            Toast.makeText(context, "Guardabosque actualizado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarGuardabosque() {
        if (currentGuardabosqueId == -1) {
            Toast.makeText(context, "Busca un guardabosque primero", Toast.LENGTH_SHORT).show()
            return
        }

        val rowsDeleted = dbHelper.eliminarGuardabosque(currentGuardabosqueId)
        if (rowsDeleted > 0) {
            Toast.makeText(context, "Guardabosque eliminado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        nombreEditText.text.clear()
        edadEditText.text.clear()
        aniosServicioEditText.text.clear()
        especialidadEditText.text.clear()
        descripcionEditText.text.clear()
        currentGuardabosqueId = -1
    }
}
