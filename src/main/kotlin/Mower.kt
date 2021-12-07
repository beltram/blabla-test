/**
 * Mows grass of a [Grid]
 * @param coordinate current position
 * @param orientation cardinal orientation
 */
data class Mower(val coordinate: Coordinate, val orientation: Orientation) {

    constructor(x: Int, y: Int, orientation: Orientation) : this(Coordinate(x, y), orientation)

    companion object {
        fun from(raw: String): Mower = TODO()
    }

    fun execute(command: Command): Mower = TODO()
}