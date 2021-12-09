/**
 * Parses input instruction file and launches scenario
 */
data class Scenario(val grid: Grid, val moves: Map<Mower, List<Command>>) {

    fun run() = grid.simulate(moves)

    companion object {
        fun from(input: String) = input.lines().let { lines ->
            val grid = Grid.from(lines.first())
            val moves = lines.drop(1).windowed(2, 2)
                .associate { (m, c) -> Mower.from(m) to c.toCharArray().map { Command.from(it) } }
            Scenario(grid, moves)
        }
    }
}