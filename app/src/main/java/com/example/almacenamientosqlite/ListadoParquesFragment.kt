package com.example.almacenamientosqlite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListadoParquesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ParquesAdapter
    private lateinit var databaseHelper: ParqueDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado_parques, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewParques)
        recyclerView.layoutManager = LinearLayoutManager(context)

        databaseHelper = ParqueDatabaseHelper(requireContext())
        val parquesList = databaseHelper.obtenerParques()

        adapter = ParquesAdapter(parquesList)
        recyclerView.adapter = adapter

        return view
    }
}
