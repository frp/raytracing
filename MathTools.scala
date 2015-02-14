import scala.math._

object MathTools {
  def solveSquareEquation(a: Double, b: Double, c: Double) = {
    val d = b * b - 4 * a * c
    if (d < 0)
      List[Double]()
    else if (d == 0)
      List(-b / (2*a))
    else
      List((-b-sqrt(d)) / (2*a), (-b+sqrt(d)) / (2*a))
  }
}
