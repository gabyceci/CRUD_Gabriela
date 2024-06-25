package gabriela.arevalo.perfil2_crud

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imvCrearTicket = findViewById<ImageView>(R.id.imvCrear)
        val imvVer = findViewById<ImageView>(R.id.imvVer)

        imvCrearTicket.setOnClickListener {
            val pantallaCrearTicket = Intent(this, CrearTicket::class.java)
            startActivity(pantallaCrearTicket)
        }

        imvVer.setOnClickListener {
            val pantallaVerTicket = Intent(this, VerTickets::class.java)
            startActivity(pantallaVerTicket)
        }


    }
}