/**
 * [Mower] cardinal orientation
 */
enum class Orientation {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        private val values = enumValues<Orientation>()
    }

    fun rotateRight(): Orientation = TODO()
    fun rotateLeft(): Orientation = TODO()
}