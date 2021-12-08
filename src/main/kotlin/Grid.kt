typealias Action = Pair<Command, Mower>

/**
 * Delimited area where the [Mower] mows
 * @param dimension area's limits
 */
data class Grid(val dimension: Coordinate) {

    constructor(x: Int, y: Int) : this(Coordinate(x, y))

    fun simulate(moves: Map<Mower, List<Command>>): List<Mower> {
        return moves.takeUnless { it.isEmpty() }
            ?.associateByCommand()
            ?.applyActions()
            .orEmpty()
    }

    private fun Map<Mower, List<Command>>.associateByCommand(): List<List<Action>> {
        return toList()
            .map { (mower, moves) -> moves.map { cmd -> listOf(cmd to mower) } }
            .reduce { a, b -> a.zip(b) { c, d -> c + d } }
    }

    private fun List<List<Action>>.applyActions(): List<Mower> {
        val current = first().executeAction()
        return when {
            size < 2 -> current.map { (_, m) -> m }
            else -> drop(1).map { it.zip(current) { (newCmd, _), (_, curMow) -> newCmd to curMow } }.applyActions()
        }
    }

    private fun List<Action>.executeAction(): List<Action> {
        val newActions = map { (c, m) -> c to applyCommand(c, m) }
        val duplicates = newActions.duplicateCoordinates()
        return newActions.zip(this) { new, old ->
            if (new.second.coordinate in duplicates) {
                when (old.first) {
                    Command.FORWARD -> old
                    else -> new
                }
            } else new
        }
    }

    private fun List<Action>.duplicateCoordinates(): Set<Coordinate> {
        return groupingBy { (_, m) -> m.coordinate }
            .eachCount()
            .filterValues { it > 1 }
            .keys
    }

    internal fun applyCommand(command: Command, mower: Mower): Mower {
        return mower.execute(command)
            .takeUnless { it.isOutsideBounds() }
            ?: mower
    }

    private fun Mower.isOutsideBounds() = coordinate.isGreaterThat(dimension)
}
