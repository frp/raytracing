import scala.math._

object MathTools {
  def colorToInt(color: Vector3) = {
    val color255 = color.cutValuesAbove(1) * 255
    (color255.x.toInt << 16) | (color255.y.toInt << 8) | color255.z.toInt
  }

  def intToColor(color: Int) = Vector3((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff) / 255.0
}
