import Command.*
import Orientation.*

/**
 * Mows grass of a [Grid]
 * @param coordinate current position
 * @param orientation cardinal orientation
 * @param isStopped has the Mower exhausted its commands
 */
data class Mower(val coordinate: Coordinate, val orientation: Orientation, var isStopped: Boolean = false) {

    constructor(x: Int, y: Int, orientation: Orientation) : this(Coordinate(x, y), orientation)

    companion object {
        fun from(raw: String): Mower {
            return raw.toCharArray()
                .let { (x, y, o) -> Mower(Coordinate(x.digitToInt(), y.digitToInt()), Orientation.from(o)) }
        }
    }

    fun execute(command: Command) = when {
        isStopped -> this
        else -> when (command) {
            LEFT -> copy(orientation = orientation.rotateLeft())
            RIGHT -> copy(orientation = orientation.rotateRight())
            FORWARD -> moveForward()
        }
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Mower

        if (coordinate != other.coordinate) return false
        if (orientation != other.orientation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinate.hashCode()
        result = 31 * result + orientation.hashCode()
        return result
    }


}