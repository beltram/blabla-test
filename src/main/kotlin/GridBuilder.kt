internal fun Map<Mower, List<Command>>.associateByCommand(): GroupedActions {
    return toList()
        .map { (mower, moves) -> moves.map { cmd -> listOf(cmd to mower) } }
        .reduce { a, b -> a.fillShortest(b).let { (c, d) -> c.zip(d) { e, f -> listOf(e, f).flatten() } } }
}

private fun GroupedActions.fillShortest(other: GroupedActions) = when {
    size > other.size -> this to other.fill(size - other.size)
    size < other.size -> fill(other.size - size) to other
    else -> this to other
}

private fun GroupedActions.fill(len: Int): MutableList<Actions> {
    val last = last()
    return toMutableList().apply {
        addAll((0 until len).map { last.map { (c, m) -> c to m.copy(isStopped = true) } })
    }
}