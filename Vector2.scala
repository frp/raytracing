case class Vector2(x: Double, y: Double) {
  def *(b: Double) = Vector2(x*b, y*b)
  def +(b: Vector2) = Vector2(x + b.x, y + b.y)
}
