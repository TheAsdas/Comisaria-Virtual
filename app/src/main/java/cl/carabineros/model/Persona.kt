package cl.carabineros.model

class Persona(
    var id: Int,
    var nombre: String,
    var segundoNombre: String,
    var apellidoPaterno: String,
    var apellidoMaterno: String,
    var rut: String,
    var numeroSerie: String,
    var genero: Int,
    var region: Int,
    var comuna: Int,
    var direccion: String,
    var claveUnica: String,
    var correo: String
)