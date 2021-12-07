import Orientation.*
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

internal class OrientationTest {

    @Test
    fun `should rotate right`() {
        assertThat(NORTH.rotateRight()).isEqualTo(EAST)
        assertThat(EAST.rotateRight()).isEqualTo(SOUTH)
        assertThat(SOUTH.rotateRight()).isEqualTo(WEST)
        assertThat(WEST.rotateRight()).isEqualTo(NORTH)
    }

    @Test
    fun `should rotate left`() {
        assertThat(NORTH.rotateLeft()).isEqualTo(WEST)
        assertThat(WEST.rotateLeft()).isEqualTo(SOUTH)
        assertThat(SOUTH.rotateLeft()).isEqualTo(EAST)
        assertThat(EAST.rotateLeft()).isEqualTo(NORTH)
    }
}