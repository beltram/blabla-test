import Command.*
import Orientation.*
import org.assertj.core.api.AssertionsForInterfaceTypes.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class MowerTest {

    @Nested
    inner class Invariants {

        @Test
        fun `should accept coordinates 0,0`() {
            assertThatNoException().isThrownBy { Mower(0, 0, NORTH) }
        }

        @Test
        fun `should not accept negative x coordinates`() {
            assertThatExceptionOfType(IllegalStateException::class.java)
                .isThrownBy { Mower(-1, 0, NORTH) }
        }

        @Test
        fun `should not accept negative y coordinates`() {
            assertThatExceptionOfType(IllegalStateException::class.java)
                .isThrownBy { Mower(0, -1, NORTH) }
        }
    }

    @Nested
    inner class Move {

        @ParameterizedTest
        @EnumSource(Orientation::class)
        fun `moving should not change orientation`(orientation: Orientation) {
            with(Mower(1, 1, orientation)) {
                assertThat(execute(FORWARD).orientation).isEqualTo(orientation)
            }
        }

        @Test
        fun `should move towards north`() {
            assertThat(Mower(1, 1, NORTH).execute(FORWARD)).isEqualTo(Mower(1, 2, NORTH))
        }

        @Test
        fun `should move towards east`() {
            assertThat(Mower(1, 1, EAST).execute(FORWARD)).isEqualTo(Mower(2, 1, EAST))
        }

        @Test
        fun `should move towards south`() {
            assertThat(Mower(1, 1, SOUTH).execute(FORWARD)).isEqualTo(Mower(1, 0, SOUTH))
        }

        @Test
        fun `should move towards west`() {
            assertThat(Mower(1, 1, WEST).execute(FORWARD)).isEqualTo(Mower(0, 1, WEST))
        }

        @Test
        fun `should not move out of origin`() {
            Mower(0, 0, WEST).also { m -> assertThat(m.execute(FORWARD)).isEqualTo(m) }
            Mower(0, 0, SOUTH).also { m -> assertThat(m.execute(FORWARD)).isEqualTo(m) }
        }
    }

    @Nested
    inner class Rotate {

        @Test
        fun `rotate should not change position`() {
            with(Mower(1, 1, NORTH)) {
                assertThat(execute(RIGHT).coordinate).isEqualTo(Coordinate(coordinate.x, coordinate.y))
            }
            with(Mower(1, 1, NORTH)) {
                assertThat(execute(LEFT).coordinate).isEqualTo(Coordinate(coordinate.x, coordinate.y))
            }
        }

        @Test
        fun `should rotate right`() {
            assertThat(Mower(0, 0, NORTH).execute(RIGHT)).isEqualTo(Mower(0, 0, EAST))
            assertThat(Mower(0, 0, EAST).execute(RIGHT)).isEqualTo(Mower(0, 0, SOUTH))
            assertThat(Mower(0, 0, SOUTH).execute(RIGHT)).isEqualTo(Mower(0, 0, WEST))
            assertThat(Mower(0, 0, WEST).execute(RIGHT)).isEqualTo(Mower(0, 0, NORTH))
        }

        @Test
        fun `should rotate left`() {
            assertThat(Mower(0, 0, NORTH).execute(LEFT)).isEqualTo(Mower(0, 0, WEST))
            assertThat(Mower(0, 0, WEST).execute(LEFT)).isEqualTo(Mower(0, 0, SOUTH))
            assertThat(Mower(0, 0, SOUTH).execute(LEFT)).isEqualTo(Mower(0, 0, EAST))
            assertThat(Mower(0, 0, EAST).execute(LEFT)).isEqualTo(Mower(0, 0, NORTH))
        }
    }
}