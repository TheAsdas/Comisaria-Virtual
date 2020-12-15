package cl.carabineros.comisariaVirtual.model

import java.io.Serializable;

data class Persona(
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
    var correo: String)
    : Serializable
{
    override fun toString(): String {
        return "$nombre $apellidoPaterno";
    }
}