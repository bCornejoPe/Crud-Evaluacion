package modelo

import java.util.UUID

data class Ticket(
    val uuid: String,
    var Autor: String,
    var Titulo: String,
    var Decripcion: String,
    var CorreoAutor: String,
    var FechaCreacion: String,
    var Estado: String,
    var FechaFinalizacion:  String,


)
