package com.example.almacenamientosqlite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction

class MenuActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Configurar la Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Esto configura la Toolbar como la ActionBar

        // Cargar el fragmento por defecto
        //loadFragment(ParquesFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflar el menú de opciones en la Toolbar
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Manejar las opciones del menú
        return when (item.itemId) {
            R.id.menu_parques -> {
                //loadFragment(ParquesFragment())
                true
            }

            R.id.menu_listado_parques -> {
                //loadFragment(ListadoParquesFragment())
                true
            }

            R.id.menu_guardabosques -> {
                //loadFragment(GuardabosquesFragment())
                true
            }

            R.id.menu_listado_guardabosques -> {
                //loadFragment(ListadoGuardabosquesFragment())
                true
            }

            R.id.menu_cerrar_sesion -> {
                finish() // Cierra la actividad y vuelve a la pantalla de login
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        // Cargar el fragmento seleccionado en el FrameLayout
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}
