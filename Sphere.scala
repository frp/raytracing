import scala.math._
import MathTools._

case class Sphere(centre: Vector3, radius: Double, material: Material) extends Shape {
  def intersect(r: Ray) = {
    val d_base = -(r.direction * (r.origin - centre))
    val discriminant = pow(r.direction * (r.origin - centre), 2) - (r.origin - centre).sqr + radius * radius
    if (discriminant >= 0) {
      val d1 = d_base + sqrt(discriminant)
      val d2 = d_base - sqrt(discriminant)
      if (d1 < 0)
        None
      else {
        val d = if (d2 >= 0) min(d1, d2) else d1
        val n = r.origin + r.direction * d - centre
        val sphericCoords = Vector2(acos(n.z / n.length), atan2(n.y, n.x))
        Some(d, sphericCoords)
      }
    }
    else
      None
  }

  def normal(c: Vector2) = Vector3(sin(c.x) * cos(c.y), sin(c.x) * sin(c.y), cos(c.x))
}

object Sphere {
  def apply(x: Double, y: Double, z: Double, r: Double, color: Int, specular: Int, shininess: Double):Sphere =
    Sphere(Vector3(x,y,z), r, Material(intToColor(color), intToColor(specular), shininess))
}
