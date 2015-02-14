import scala.math._
import MathTools._

class Scene(width: Int, height: Int, fovX: Double) {
  var spheres = List[Sphere]()

  def addSphere(s: Sphere) {
    spheres = s :: spheres
  }

  def addSphere(x: Double, y: Double, z: Double, r: Double, c: Int) {
    spheres = Sphere(Vector3(x, y, z), r, c) :: spheres
  }

  var lights = List[Light]()

  def addLight(l: Light) {
    lights = l :: lights
  }

  def addLight(x: Double, y: Double, z: Double, i: Double) {
    lights = Light(Vector3(x,y,z), i) :: lights
  }

  val renderPlane = width / (2 * tan(fovX / 2))

  def traceRay(ray: Ray) = spheres.foldLeft((-1.0, null: Sphere)) { (result, sphere) =>
    val (distance, orig_sphere) = result
    sphere.intersect(ray) match {
      case Some(d) => if (distance == -1 || d < distance) (d, sphere) else (distance, orig_sphere)
      case None => (distance, orig_sphere)
    }
  }

  def applyShading(ray: Ray, distance: Double, sphere: Sphere) = {
    val intersection_point = ray.direction * distance + ray.origin
    val normal = (intersection_point - sphere.centre).normalize
    val intensity = lights.foldLeft(0.0) { (sum, light) =>
      val lightsource_dir = (light.origin - intersection_point).normalize
      sum + lightsource_dir * normal * light.intensity
    }

    applyIntensityToColor(sphere.color, intensity)
  }

  def renderPixel(x: Int, y: Int) = {
    val ray = new Ray(Vector3(0, 0, 0), Vector3(x - width/2, height/2 - y, renderPlane))

    val (distance, sphere) = traceRay(ray)
    if (sphere == null)
      0
    else
      applyShading(ray, distance, sphere)
  }

  def render(callback: (Int, Int, Int) => Unit) =
    for (i <- 0 until width; j <- 0 until height)
      callback(i, j, renderPixel(i, j))
}
