package cl.carabineros.comisariaVirtual.model

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
class Regiones(
    var Regiones: ArrayList<Region>)

class Region(
    var IdRegion: Int,
    var NombreRegion: String)
{
    override fun toString(): String {
        return this.NombreRegion;
    }
}

class Comuna(
    var IdCiudad: Int,
    var Ciudad: String)
{
    override fun toString(): String {
        return this.Ciudad;
    }
}