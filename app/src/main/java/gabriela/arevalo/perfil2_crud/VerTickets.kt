package gabriela.arevalo.perfil2_crud

import RecyclerViewHelpers.Adaptador
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tickets
import java.util.UUID

class VerTickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)
        val imvAtras = findViewById<ImageView>(R.id.imvAtras)


        imvAtras.setOnClickListener {
            val atras = Intent(this, PaginaInicio::class.java)
            startActivity(atras)
        }


        rcvTickets.layoutManager = LinearLayoutManager(this)

        fun obtenerDatos(): List<tickets> {
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from Tickets")!!

            val listadoTickets = mutableListOf<tickets>()

            while (resultSet.next()){
                val uuid = resultSet.getString("UUID_NumeroTicket")
                val titulo = resultSet.getString("TituloTicket")
                val descripcion = resultSet.getString("DescripcionTicket")
                val responsable = resultSet.getString("ResponsableTicket")
                val correoResponsable = resultSet.getString("CorreoResponsable")
                val telefono = resultSet.getString("TelefonoResponsable")
                val ubicacion = resultSet.getString("Ubicacion")
                val estadoTicket = resultSet.getString("EstadoTicket")

                val producto = tickets(uuid, titulo, descripcion, responsable, correoResponsable, telefono, ubicacion, estadoTicket)
                listadoTickets.add(producto)
            }
            return listadoTickets
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ejecutarFuncion = obtenerDatos()


            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(ejecutarFuncion)
                rcvTickets.adapter = miAdaptador
            }

            val nuevosProductos = obtenerDatos()

            //Creo una corrutina que actualice el listado
            withContext(Dispatchers.Main){
                (rcvTickets.adapter as? Adaptador)?.actualizarRecyclerView(nuevosProductos)
            }
        }
    }

}





