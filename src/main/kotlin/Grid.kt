typealias Action = Pair<Command, Mower>
typealias Actions = List<Action>
typealias GroupedActions = List<Actions>

/**
 * Delimited area where the [Mower] mows
 * @param dimension area's limits
 */
data class Grid(val dimension: Coordinate) {

    companion object {
        fun from(raw: String) = raw.toCharArray().map { it.digitToInt() }.let { (x, y) -> Grid(x, y) }
    }

    constructor(x: Int, y: Int) : this(Coordinate(x, y))

    fun simulate(moves: Map<Mower, List<Command>>): List<Mower> {
        return moves.takeUnless { it.isEmpty() }
            ?.associateByCommand()
            ?.applyActions()
            .orEmpty()
    }

    private fun GroupedActions.applyActions(): List<Mower> {
        return first().executeAction().let { current ->
            when {
                size < 2 -> current.map { it.second }
                else -> drop(1).map { next -> current.mergeWith(next) }.applyActions()
            }
        }
    }

    private fun Actions.mergeWith(next: Actions): Actions {
        return zip(next) { (_, currMow), (nextCmd, nextMow) -> nextCmd to currMow.copy(isStopped = nextMow.isStopped) }
    }

    private fun Actions.executeAction(): Actions {
        val (nextActions, duplicates) = nextActions().let { it to it.duplicateCoordinates() }
        return zip(nextActions) { prev, next ->
            if (next.second.coordinate in duplicates && prev.first == Command.FORWARD) prev
            else next
        }
    }

    private fun Actions.nextActions() = map { (c, m) -> c to applyCmd(c, m) }

    private fun Actions.duplicateCoordinates(): Set<Coordinate> {
        return groupingBy { (_, m) -> m.coordinate }
            .eachCount()
            .filterValues { it > 1 }
            .keys
    }

    internal fun applyCmd(cmd: Command, mower: Mower) = mower.execute(cmd).takeUnless { it.isOutsideBounds() } ?: mower

    private fun Mower.isOutsideBounds() = coordinate.isGreaterThat(dimension)
}
