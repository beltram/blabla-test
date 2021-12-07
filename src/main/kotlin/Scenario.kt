/**
 * Parses input instruction file and launches scenario
 */
class Scenario(val grid: Grid, val moves: Map<Mower, List<Command>>) {

    fun run(): List<Mower> = TODO()

    companion object {
        fun from(input: String): Scenario {
            TODO()
        }
    }
}