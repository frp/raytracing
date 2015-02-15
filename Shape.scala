trait Shape {
  def material: Material
  def intersect(ray: Ray): Option[(Double, Vector2)]
  def normal(coords: Vector3): Vector3

  def texture(coords: Vector3) = Vector3(1, 1, 1)
}
