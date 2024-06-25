package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gabriela.arevalo.perfil2_crud.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.txtNombreTicket)
    val txtEstado: TextView = view.findViewById(R.id.txtEstado)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)

}