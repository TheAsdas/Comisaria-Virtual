package cl.carabineros.model

/**
 * Sector: this class is the objects received through the API. They represent a sector
 * of Chile, may it be a Region or a Commune.
 *
 * @property id Identifier (Int
 * @property type
 * @property name
 * @property latitude
 * @property longitude
 * @property url
 * @property parentId
 * @constructor Create empty Sector
 */
class Sector(
    var id: Int,
    var type: String,
    var name: String,
    var latitude: Float,
    var longitude: Float,
    var url: String,
    var parentId: Int)
{
    override fun toString(): String {
        return name;
    }
}