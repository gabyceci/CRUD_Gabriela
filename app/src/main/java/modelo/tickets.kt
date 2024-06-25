package modelo

data class tickets(
    val UUID_NumeroTicket: String,
    var TituloTicket: String,
    var DescripcionTicket: String,
    var ResponsableTicket: String,
    var CorreoResponsable: String,
    var TelefonoResponsable: String,
    var Ubicacion: String,
    var EstadoTicket: String
)
