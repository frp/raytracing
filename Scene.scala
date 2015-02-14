import scala.math._

class Scene(width: Int, height: Int, fovX: Double) {
  var spheres = List[Sphere]()

  def addSphere(s: Sphere) {
    spheres = s :: spheres
  }

  def addSphere(x: Double, y: Double, z: Double, r: Double, c: Int) {
    spheres = Sphere(Vector3(x, y, z), r, c) :: spheres
  }

  val renderPlane = width / (2 * tan(fovX / 2))

  def renderPixel(x: Int, y: Int) = {
    val ray = new Ray(Vector3(0, 0, 0), Vector3(x - width/2, height/2 - y, renderPlane))
    (spheres.foldLeft((-1.0, 0)) { (result, sphere) =>
      val (distance, color) = result
      sphere.intersect(ray) match {
        case Some(d) => if (distance == -1 || d < distance) (d, sphere.color) else (distance, color)
        case None => (distance, color)
      }
    })._2
  }

  def render(callback: (Int, Int, Int) => Unit) =
    for (i <- 0 until width; j <- 0 until height)
      callback(i, j, renderPixel(i, j))
}
