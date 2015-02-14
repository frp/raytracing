class Ray(val origin: Vector3, dir: Vector3) {
  val direction = dir.normalize
}
