import scala.math._

object MathTools {
  def applyIntensityToColor(color: Int, intensity: Double) = {
    if (intensity >= 1)
      color
    else if (intensity < 0)
      0
    else {
      //Deconstruct the color
      val (red, green, blue) = ((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff)
      // Multiply each component by intensivity
      val (new_red, new_green, new_blue) = ((red * intensity).toInt, (green * intensity).toInt, (blue * intensity).toInt)
      (new_red << 16) | (new_green << 8) | new_blue
    }
  }
}
