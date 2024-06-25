package gabriela.arevalo.perfil2_crud

import RecyclerViewHelpers.Adaptador
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class CrearTicket : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtTituloTicket = findViewById<EditText>(R.id.txtTituloTicket)
        val txtDescripcionTicket = findViewById<EditText>(R.id.txtDescripcionTicket)
        val txtResponsableTicket = findViewById<EditText>(R.id.txtResponsableTicket)
        val txtCorreoResponsable = findViewById<EditText>(R.id.txtCorreoResponsable)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefono)
        val txtUbicacion = findViewById<EditText>(R.id.txtUbicacion)
        val txtEstadoTicket = findViewById<EditText>(R.id.txtEstadoTicket)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val imvAtras = findViewById<ImageView>(R.id.imvAtras)


        imvAtras.setOnClickListener {
            val atras = Intent(this, PaginaInicio::class.java)
            startActivity(atras)
        }


        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){


                val objConexion = ClaseConexion().cadenaConexion()


                val addProducto = objConexion?.prepareStatement("insert into Tickets(UUID_NumeroTicket, TituloTicket, DescripcionTicket, ResponsableTicket, CorreoResponsable, TelefonoResponsable, Ubicacion, EstadoTicket) values(?, ?, ?, ?, ?, ?, ?, ?)")!!
                addProducto.setString(1, UUID.randomUUID().toString())
                addProducto.setString(2, txtTituloTicket.text.toString())
                addProducto.setString(3, txtDescripcionTicket.text.toString())
                addProducto.setString(4, txtResponsableTicket.text.toString())
                addProducto.setString(5, txtCorreoResponsable.text.toString())
                addProducto.setString(6, txtTelefono.text.toString())
                addProducto.setString(7, txtUbicacion.text.toString())
                addProducto.setString(8, txtEstadoTicket.text.toString())
                addProducto.executeUpdate()


            }
        }
    }
}