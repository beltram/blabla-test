import Command.*
import Orientation.EAST
import Orientation.NORTH
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

internal class ScenarioTest {

    companion object {
        val rawScenario = this::class.java.classLoader.getResourceAsStream("input.txt")
            ?.readAllBytes()
            ?.decodeToString()
            .orEmpty()
    }

    @Test
    fun `should parse instructions correctly`() {
        assertThat(Scenario.from(rawScenario)).isEqualTo(
            Scenario(
                Grid(5, 5),
                mapOf(
                    Mower(1, 2, NORTH) to listOf(
                        LEFT,
                        FORWARD,
                        LEFT,
                        FORWARD,
                        LEFT,
                        FORWARD,
                        LEFT,
                        FORWARD,
                        FORWARD
                    ),
                    Mower(3, 3, EAST) to listOf(
                        FORWARD,
                        FORWARD,
                        RIGHT,
                        FORWARD,
                        FORWARD,
                        RIGHT,
                        FORWARD,
                        RIGHT,
                        RIGHT,
                        FORWARD
                    )
                )
            )
        )
    }

    @Test
    fun `should run as expected`() {
        assertThat(Scenario.from(rawScenario).run()).containsExactly(Mower(1, 3, NORTH), Mower(5, 1, EAST))
    }
}