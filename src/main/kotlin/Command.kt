/**
 * [Mower]'s possible commands
 * @property LEFT rotate the [Mower] to the left
 * @property RIGHT rotate the [Mower] to the right
 * @property FORWARD advance the [Mower]'s position by 1
 */
enum class Command {
    LEFT, RIGHT, FORWARD;

    companion object {
        fun from(raw: Char) = when (raw) {
            'L' -> LEFT
            'R' -> RIGHT
            'F' -> FORWARD
            else -> throw IllegalStateException("Invalid command")
        }
    }
}