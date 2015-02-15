import scala.math._

case class Sphere(centre: Vector3, radius: Double, material: Material) {
  def intersect(r: Ray) = {
    val d_base = -(r.direction * (r.origin - centre))
    val discriminant = pow(r.direction * (r.origin - centre), 2) - (r.origin - centre).sqr + radius * radius
    if (discriminant >= 0) {
      val d1 = d_base + sqrt(discriminant)
      val d2 = d_base - sqrt(discriminant)
      if (d1 < 0)
        None
      else
        Some(if (d2 >= 0) min(d1, d2) else d1)
    }
    else
      None
  }
}
