import Command.*
import Orientation.*

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

    fun execute(command: Command) = when (command) {
        LEFT -> copy(orientation = orientation.rotateLeft())
        RIGHT -> copy(orientation = orientation.rotateRight())
        FORWARD -> moveForward()
    }

    private fun moveForward(): Mower {
        return when (orientation) {
            NORTH -> coordinate.copy(y = coordinate.y + 1)
            EAST -> coordinate.copy(x = coordinate.x + 1)
            SOUTH -> coordinate.y.minus(1).takeUnless { it < 0 }?.let { coordinate.copy(y = it) }
            WEST -> coordinate.x.minus(1).takeUnless { it < 0 }?.let { coordinate.copy(x = it) }
        }
            ?.let { copy(coordinate = it) }
            ?: this
    }
}