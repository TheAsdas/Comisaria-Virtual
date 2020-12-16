package cl.carabineros.comisariaVirtual.model

class Address(
    var id: Int,
    var nombreLugar: String,
    var tipo: Int,
    var region: Int,
    var comuna: Int,
    var direccion: String
)