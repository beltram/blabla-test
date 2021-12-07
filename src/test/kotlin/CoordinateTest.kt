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
}