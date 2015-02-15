import MathTools._

case class Triangle(v0: Vector3, v1: Vector3, v2: Vector3, material: Material) extends Shape {
  // Möller–Trumbore intersection algorithm
  // http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf
  override def intersect(ray: Ray) = {
    solveVectorEquation(-ray.direction, v1-v0, v2-v0, ray.origin - v0) match {
      case Some(x) =>
        if (x.y >= 0 && x.z >= 0 && x.y + x.z <= 1)
          Some((x.x, Vector2(x.y, x.z)))
        else
          None
      case None => None
    }
  }

  override def normal(c: Vector2) = (v1 - v0).cross(v2 - v0).normalize
}
