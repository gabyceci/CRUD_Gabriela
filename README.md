CREATE TABLE Usuarios(
  UUID_Usuario VARCHAR2(50) PRIMARY KEY,
  Correo VARCHAR2(100) NOT NULL UNIQUE,
  Contraseña VARCHAR2(255) NOT NULL
);

select * from Usuarios;


create table Tickets(
    UUID_NumeroTicket VARCHAR2(50) PRIMARY KEY,
    TituloTicket VARCHAR2(50) NOT NULL,
    DescripcionTicket VARCHAR2(200) NOT NULL,
    ResponsableTicket VARCHAR2(200) NOT NULL,
    CorreoResponsable VARCHAR2(200) NOT NULL,
    TelefonoResponsable VARCHAR2(200) NOT NULL,
    Ubicacion VARCHAR2(200) NOT NULL,
    EstadoTicket VARCHAR2(200) NOT NULL
);

select * from Tickets;
