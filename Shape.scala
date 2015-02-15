trait Shape {
  def material: Material
  def intersect(ray: Ray): Option[(Double, Vector2)]
  def normal(coords: Vector2): Vector3
}
