/**
 * [Mower]'s cardinal orientation
 */
enum class Orientation {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        private val values = enumValues<Orientation>()
        private val len = values.size
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