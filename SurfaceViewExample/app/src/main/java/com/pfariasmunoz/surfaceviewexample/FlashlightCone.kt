package com.pfariasmunoz.surfaceviewexample

/**
 * Represents the cone of a flashlight with a radius that's proportional to the smaller
 * screen dimension of the device. Has get methods for the location and size of the
 * cone and a set method for the cone's location.
 */
class FlashlightCone(private val viewWidth: Int, private val viewHeight: Int) {
    var x: Int = viewWidth / 2
        private set
    var y: Int = viewHeight / 2
        private set

    // Adjust the radius for the narrowest view dimension.
    val radius: Int
        get() = if (viewWidth <= viewHeight) x / 3 else y / 3

    fun update(newX: Int, newY: Int) {
        x = newX
        y = newY
    }
}