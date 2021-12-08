import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThatExceptionOfType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CoordinateTest {

    @Nested
    inner class Invariants {

        @Test
        fun `should not accept negative x coordinates`() {
            assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy { Coordinate(-1, 0) }
        }

        @Test
        fun `should not accept negative y coordinates`() {
            assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy { Coordinate(0, -1) }
        }
    }

    @Nested
    inner class GreaterThan {

        @Test
        fun `higher should be greater than`() {
            assertThat(Coordinate(2, 2).isGreaterThat(Coordinate(1, 1))).isTrue
        }

        @Test
        fun `higher only horizontally should be greater than`() {
            assertThat(Coordinate(2, 2).isGreaterThat(Coordinate(1, 2))).isTrue
        }

        @Test
        fun `higher only vertically should be greater than`() {
            assertThat(Coordinate(2, 2).isGreaterThat(Coordinate(2, 1))).isTrue
        }

        @Test
        fun `lower should not be greater than`() {
            assertThat(Coordinate(1, 1).isGreaterThat(Coordinate(2, 2))).isFalse
        }

        @Test
        fun `equal should not be greater than`() {
            assertThat(Coordinate(1, 1).isGreaterThat(Coordinate(1, 1))).isFalse
        }
    }
}