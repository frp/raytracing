class Ray(val origin: Vector3, dir: Vector3) {
  val direction = dir.normalize

  override def toString = "Ray(" + origin.toString + "," + direction.toString + ")"
}
