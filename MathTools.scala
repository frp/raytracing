import scala.math._

object MathTools {
  def colorToInt(color: Vector3) = {
    val color255 = color.cutValuesAbove(1) * 255
    (color255.x.toInt << 16) | (color255.y.toInt << 8) | color255.z.toInt
  }

  def intToColor(color: Int) = Vector3((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff) / 255.0

  /** Find the determinant of the 3x3 matrix formed from vectors
    *
    * | x1 x2 x3 |
    * | y1 y2 y3 |
    * | z1 z2 z3 |
    */
  def det3(v1: Vector3, v2: Vector3, v3: Vector3) =
    v1.x * (v2.y * v3.z - v3.y * v2.z) - v2.x * (v1.y * v3.z - v3.y * v1.z) + v3.x * (v1.y * v2.z - v2.y * v1.z)

  /** Solve vector equation
    *
    * [V1, V2, V3] * X = Vr
    * Where V1, v2, V3, X, Vr - vectors
    *
    * The equation is solved using Cramer's rule
    */
  def solveVectorEquation(v1: Vector3, v2: Vector3, v3: Vector3, vr: Vector3):Option[Vector3] = {
    val d = det3(v1, v2, v3)
    val d1 = det3(vr, v2, v3)
    val d2 = det3(v1, vr, v3)
    val d3 = det3(v1, v2, vr)
    if (d == 0)
      None
    else
      Some(Vector3(d1/d, d2/d, d3/d))
  }
}
