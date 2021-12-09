/**
 * [Mower]'s cardinal orientation
 */
enum class Orientation {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        fun from(raw: Char) = when (raw) {
            'N' -> NORTH
            'E' -> EAST
            'S' -> SOUTH
            'W' -> WEST
            else -> throw IllegalStateException("Invalid orientation")
        }
    }

    fun rotateRight() = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun rotateLeft() = when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }
}