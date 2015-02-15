import MathTools._

case class Triangle(v0: Vector3, v1: Vector3, v2: Vector3, t0: Vector2, t1: Vector2, t2: Vector2, material: Material) extends Shape {
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

  override def normal(c: Vector3) = (v1 - v0).cross(v2 - v0).normalize

  override def texture(c: Vector3) =
    if (material.texture != null) {
      val f0 = c - v0
      val f1 = c - v1
      val f2 = c - v2

      val a = (v0 - v1).cross(v0 - v2).length
      val a0 = f1.cross(f2).length / a
      val a1 = f2.cross(f0).length / a
      val a2 = f0.cross(f1).length / a

      val texcoord = t0 * a0 + t1 * a1 + t2 * a2

      material.textureColor(texcoord)
    }
    else
      Vector3(1, 1, 1)
}

object Triangle {
  def apply(v0: Vector3, v1: Vector3, v2: Vector3, material: Material): Triangle =
    Triangle(v0, v1, v2, Vector2(0, 0), Vector2(0, 0), Vector2(0, 0), material)
}
