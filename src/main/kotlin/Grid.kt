/**
 * Delimited area where the [Mower] mows
 * @param dimension area's limits
 */
data class Grid(val dimension: Coordinate) {

    constructor(x: Int, y: Int) : this(Coordinate(x, y))

    fun simulate(moves: Map<Mower, List<Command>>): List<Mower> = TODO()

    internal fun applyCommand(mower: Mower, command: Command): Mower = TODO()
}
