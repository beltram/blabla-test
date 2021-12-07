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
            assertThat(Grid(10, 10).applyCommand(Mower(0, 0, NORTH), FORWARD)).isEqualTo(Mower(0, 1, NORTH))
        }

        @Test
        fun `should apply rotate command within bounds`() {
            assertThat(Grid(10, 10).applyCommand(Mower(0, 0, NORTH), RIGHT)).isEqualTo(Mower(0, 1, EAST))
            assertThat(Grid(10, 10).applyCommand(Mower(0, 0, NORTH), LEFT)).isEqualTo(Mower(0, 1, WEST))
        }

        @Test
        fun `should not apply forward command outside horizontal bounds`() {
            Mower(10, 10, EAST).also { m -> assertThat(Grid(10, 10).applyCommand(m, FORWARD)).isEqualTo(m) }
        }

        @Test
        fun `should not apply forward command outside vertical bounds`() {
            Mower(10, 10, NORTH).also { m -> assertThat(Grid(10, 10).applyCommand(m, FORWARD)).isEqualTo(m) }
        }
    }

    @Nested
    inner class Simulation {

        @Test
        fun `should not move moving mowers when about to collide`() {
            val leftMower = Mower(0, 0, EAST)
            val rightMower = Mower(2, 0, WEST)
            val moves = mapOf(leftMower to listOf(FORWARD), rightMower to listOf(FORWARD))
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(leftMower, rightMower)
        }

        @Test
        fun `moving mower about to collide another should not impeach the latter to rotate`() {
            val leftMower = Mower(0, 0, EAST)
            val rightMower = Mower(1, 0, WEST)
            val moves = mapOf(leftMower to listOf(FORWARD), rightMower to listOf(RIGHT))
            assertThat(Grid(10, 10).simulate(moves)).containsExactly(leftMower, rightMower.copy(orientation = NORTH))
        }
    }
}