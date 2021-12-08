import Command.*
import Orientation.*
import org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GridTest {

    @Nested
    inner class Invariants {

        @Test
        fun `should not accept invalid dimensions`() {
            assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy { Grid(-1, 0) }
            assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy { Grid(0, -1) }
        }
    }

    @Nested
    inner class ApplyCommand {

        @Test
        fun `should apply forward command within bounds`() {
            assertThat(Grid(10, 10).applyCommand(FORWARD, Mower(0, 0, NORTH))).isEqualTo(Mower(0, 1, NORTH))
        }

        @Test
        fun `should apply rotate command within bounds`() {
            assertThat(Grid(10, 10).applyCommand(RIGHT, Mower(0, 0, NORTH))).isEqualTo(Mower(0, 0, EAST))
            assertThat(Grid(10, 10).applyCommand(LEFT, Mower(0, 0, NORTH))).isEqualTo(Mower(0, 0, WEST))
        }

        @Test
        fun `should not apply forward command outside horizontal bounds`() {
            Mower(10, 10, EAST).also { m -> assertThat(Grid(10, 10).applyCommand(FORWARD, m)).isEqualTo(m) }
        }

        @Test
        fun `should not apply forward command outside vertical bounds`() {
            Mower(10, 10, NORTH).also { m -> assertThat(Grid(10, 10).applyCommand(FORWARD, m)).isEqualTo(m) }
        }
    }

    @Nested
    inner class Simulation {

        @Test
        fun `should move mowers`() {
            val moves = mapOf(
                Mower(0, 0, NORTH) to listOf(FORWARD),
                Mower(2, 0, EAST) to listOf(FORWARD),
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(0, 1, NORTH),
                Mower(3, 0, EAST),
            )
        }

        @Test
        fun `mowers should do many moves`() {
            val moves = mapOf(
                Mower(0, 0, NORTH) to listOf(FORWARD, FORWARD),
                Mower(2, 0, EAST) to listOf(FORWARD, FORWARD),
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(0, 2, NORTH),
                Mower(4, 0, EAST),
            )
        }

        @Test
        fun `should move many mowers`() {
            val moves = mapOf(
                Mower(0, 0, NORTH) to listOf(FORWARD, RIGHT),
                Mower(2, 0, EAST) to listOf(FORWARD, RIGHT),
                Mower(2, 1, EAST) to listOf(FORWARD, RIGHT),
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(0, 1, EAST),
                Mower(3, 0, SOUTH),
                Mower(3, 1, SOUTH),
            )
        }

        @Test
        fun `should not move moving mowers when about to collide`() {
            val moves = mapOf(
                Mower(0, 0, EAST) to listOf(FORWARD),
                Mower(2, 0, WEST) to listOf(FORWARD)
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(0, 0, EAST),
                Mower(2, 0, WEST),
            )
        }

        @Test
        fun `moving mower about to collide another should not impeach the latter to rotate`() {
            val moves = mapOf(
                Mower(0, 0, EAST) to listOf(FORWARD),
                Mower(1, 0, WEST) to listOf(RIGHT),
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(0, 0, EAST),
                Mower(1, 0, NORTH),
            )
        }

        @Test
        fun `mower should take place left by another`() {
            val moves = mapOf(
                Mower(0, 0, NORTH) to listOf(FORWARD),
                Mower(1, 0, WEST) to listOf(FORWARD),
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(0, 1, NORTH),
                Mower(0, 0, WEST),
            )
        }

        @Test
        fun `mowers should be able to cross each other`() {
            val moves = mapOf(
                Mower(0, 0, EAST) to listOf(FORWARD),
                Mower(1, 0, WEST) to listOf(FORWARD),
            )
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(
                Mower(1, 0, EAST),
                Mower(0, 0, WEST),
            )
        }

        @Test
        fun `should not fail on empty actions`() {
            assertThat(Grid(10, 10).simulate(emptyMap())).isEmpty()
        }
    }
}