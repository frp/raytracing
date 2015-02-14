import scala.math._

case class Vector3(x: Double, y: Double, z: Double) {
  def length = sqrt(x*x + y*y + z*z)
  def normalize = Vector3(x / length, y / length, z / length)
  def sqr = x * x + y * y + z * z
  def *(b: Vector3) = x * b.x + y * b.y + z * b.z
  def -(b: Vector3) = Vector3(x-b.x, y-b.y, z-b.z)
  def +(b: Vector3) = Vector3(x+b.x, y+b.y, z+b.z)
  def *(b: Double) = Vector3(x*b, y*b, z*b)
}
