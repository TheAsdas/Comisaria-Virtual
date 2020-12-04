package cl.carabineros.model

/**
 * Sector: this class is the objects received through the API. They represent a sector
 * of Chile, may it be a Region or a Commune.
 *
 * @property codigo ID.
 * @property tipo Can be either a Region or Commune.
 * @property nombre Name of the region or commune.
 * @property lat Latitude.
 * @property lng Longitude.
 * @property url Web page of the sector.
 * @property codigo_padre Parent code of the region.
 * @constructor Create empty Sector
 */
data class Sector(
    var codigo: Int,
    var tipo: String,
    var nombre: String,
    var lat: Float,
    var lng: Float,
    var url: String,
    var codigo_padre: Int)
{
    override fun toString(): String {
        return nombre;
    }
}