/**
 * [Mower]'s cartesian coordinates
 * @param x horizontal axis
 * @param x vertical axis
 */
class Coordinate(val x: Int, val y: Int) {
    init {
        check(x >= 0)
        check(y >= 0)
    }
}