package gabriela.arevalo.perfil2_crud

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Tickets : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uuid = intent.getStringExtra("UUID_NumeroTicket")
        val titulo = intent.getStringExtra("TituloTicket")
        val descripcion = intent.getStringExtra("DescripcionTicket")
        val responsable = intent.getStringExtra("ResponsableTicket")
        val correoResponsable = intent.getStringExtra("CorreoResponsable")
        val telefono = intent.getStringExtra("TelefonoResponsable")
        val ubicacion = intent.getStringExtra("Ubicacion")
        val estadoTicket = intent.getStringExtra("EstadoTicket")

        val txtUUID = findViewById<TextView>(R.id.txtUUID)
        val txtTitulo = findViewById<TextView>(R.id.txtTitulo)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcion)
        val txtResponsable = findViewById<TextView>(R.id.txtResponsable)
        val txtCorreoR = findViewById<TextView>(R.id.txtCorreoR)
        val txtTelefonoR = findViewById<TextView>(R.id.txtTelefonoR)
        val txtUbicaciont = findViewById<TextView>(R.id.txtUbicaciont)
        val txtEstadot = findViewById<TextView>(R.id.txtEstadot)

        txtUUID.text = uuid
        txtTitulo.text = titulo
        txtDescripcion.text = descripcion
        txtResponsable.text = responsable
        txtCorreoR.text = correoResponsable
        txtTelefonoR.text = telefono
        txtUbicaciont.text = ubicacion
        txtEstadot.text = estadoTicket

    }
}