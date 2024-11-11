package com.example.almacenamientosqlite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListadoGuardabosquesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GuardabosquesAdapter
    private lateinit var databaseHelper: GuardabosqueDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado_guardabosques, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewGuardabosques)
        recyclerView.layoutManager = LinearLayoutManager(context)

        databaseHelper = GuardabosqueDatabaseHelper(requireContext())
        val guardabosquesList = databaseHelper.obtenerGuardabosques()

        if (guardabosquesList.isEmpty()) {
            Toast.makeText(context, "No hay guardabosques registrados", Toast.LENGTH_SHORT).show()
        }

        adapter = GuardabosquesAdapter(guardabosquesList)
        recyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        val guardabosquesList = databaseHelper.obtenerGuardabosques()
        adapter = GuardabosquesAdapter(guardabosquesList)
        recyclerView.adapter = adapter
    }
}


