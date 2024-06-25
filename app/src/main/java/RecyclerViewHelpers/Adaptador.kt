package RecyclerViewHelpers

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import gabriela.arevalo.perfil2_crud.R
import gabriela.arevalo.perfil2_crud.Tickets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tickets

class Adaptador(var Datos: List<tickets>):RecyclerView.Adapter<ViewHolder>() {


    fun actualizarRecyclerView(nuevaLista: List<tickets>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarRegistro(nombreProducto: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){

            val objConexion = ClaseConexion().cadenaConexion()

            val deleteProducto = objConexion?.prepareStatement("delete Tickets where TituloTicket = ?")!!
            deleteProducto.setString(1, nombreProducto)
            deleteProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }


    fun actualizarListadoDespuesDeEditar(uuid: String, nuevoEstado: String){
        val identificador = Datos.indexOfFirst { it.UUID_NumeroTicket == uuid}
        Datos[identificador].EstadoTicket = nuevoEstado
        notifyDataSetChanged()
        notifyItemChanged(identificador)
    }


    fun editarProducto(EstadoTicket: String,uuid: String){
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val updateProducto = objConexion?.prepareStatement("update Tickets set EstadoTicket = ? where UUID_NumeroTicket = ?")!!
            updateProducto.setString(1, EstadoTicket)
            updateProducto.setString(2, uuid)
            updateProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarListadoDespuesDeEditar(uuid, EstadoTicket)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.TituloTicket
        holder.txtEstado.text = producto.EstadoTicket
        holder.imgBorrar.setOnClickListener {

            val context = holder.textView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estas seguro que deseas eliminar?")

            builder.setPositiveButton("Si") { dialog, wich ->
                eliminarRegistro(producto.TituloTicket, position)
            }

            builder.setNegativeButton("No") { dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.imgEditar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar Estado")

            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(producto.EstadoTicket)
            builder.setView(cuadroTexto)


            builder.setPositiveButton("Confirmar"){
                    dialog, wich ->
                editarProducto(cuadroTexto.text.toString(), producto.UUID_NumeroTicket)
            }
            builder.setNegativeButton("Cancelar"){
                    dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }



        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, Tickets::class.java)

            intent.putExtra("UUID_NumeroTicket", producto.UUID_NumeroTicket)
            intent.putExtra("TituloTicket", producto.TituloTicket)
            intent.putExtra("DescripcionTicket", producto.DescripcionTicket)
            intent.putExtra("ResponsableTicket", producto.ResponsableTicket)
            intent.putExtra("CorreoResponsable", producto.CorreoResponsable)
            intent.putExtra("TelefonoResponsable", producto.TelefonoResponsable)
            intent.putExtra("Ubicacion", producto.Ubicacion)
            intent.putExtra("EstadoTicket", producto.EstadoTicket)

            context.startActivity(intent)
        }
    }
}