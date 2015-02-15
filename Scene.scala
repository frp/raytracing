import scala.math._
import MathTools._

class Scene(width: Int, height: Int, fovX: Double) {
  var spheres = List[Sphere]()

  def addSphere(s: Sphere) {
    spheres = s :: spheres
  }

  def addSphere(x: Double, y: Double, z: Double, r: Double, c: Int, s: Int, sh: Double) {
    spheres = Sphere(Vector3(x, y, z), r, Material(intToColor(c), intToColor(s), sh)) :: spheres
  }

  var lights = List[Light]()

  def addLight(l: Light) {
    lights = l :: lights
  }

  def addLight(x: Double, y: Double, z: Double, c: Int) {
    lights = Light(Vector3(x,y,z), intToColor(c)) :: lights
  }

  val renderPlane = width / (2 * tan(fovX / 2))

  var ambientLight = Vector3(0, 0, 0)

  /** Trace ray, find the first object it intersects
    *
    * @param ray the ray to trace
    * @return tuple that consists of the distance to the intersected object and the object (sphere) itself
    */
  def traceRay(ray: Ray) = spheres.foldLeft((-1.0, null: Sphere)) { (result, sphere) =>
    val (distance, orig_sphere) = result
    sphere.intersect(ray) match {
      case Some(d) => if (distance == -1 || d < distance) (d, sphere) else (distance, orig_sphere)
      case None => (distance, orig_sphere)
    }
  }

  /** Given the viewer ray, distance, object (sphere), calculate the color
    *
    * @param ray the ray from the camera to the intersection point
    * @param distance the distance from the camera to the intersection point
    * @param sphere the intersected sphere
    * @return the color
    */
  def applyShading(ray: Ray, distance: Double, sphere: Sphere) = {
    val intersection_point = ray.direction * distance + ray.origin
    val normal = (intersection_point - sphere.centre).normalize

    // calculate the sum for all lights
    // initial value is ambient light (the background light that is present without light sources)
    lights.foldLeft(sphere.material.ambient.perComponentMul(ambientLight)) { (sum, light) =>
      // direction from the intersection point to the light source
      val lightsource_dir = (light.origin - intersection_point).normalize

      // find the obstacle on the path from the intersection point to the light source
      // if there is an obstacle, the object is in the shadow, not illuminated by this light source
      // adding 0.01 is needed to avoid having intersections with itself
      val (_, obstacle) = traceRay(new Ray(intersection_point + lightsource_dir * 0.01, lightsource_dir))

      // if no obstacle, we're not in shadow
      if (obstacle == null) {
        // diffuse ligth calculation (Lambert formula)
        val diffuseDotProduct = lightsource_dir * normal
        // apply diffuse lighting only if the value is positive
        val diffuse = if (diffuseDotProduct > 0) light.color * diffuseDotProduct else Vector3(0, 0, 0)

        // specular light calculation (Phong model)
        // the reflected light vector
        val r = normal * 2 * (lightsource_dir * normal)  - lightsource_dir
        // the direction to the viewer (camera)
        val viewer_dir = ray.direction * (-1)
        val specularDotProduct = viewer_dir * r
        // we apply specular light only if both specular and diffuse light values are positive
        val specular =
          if (specularDotProduct > 0 && diffuseDotProduct > 0)
            (light.color * (viewer_dir * r)).powComponents(sphere.material.shininess)
          else
            Vector3(0, 0, 0)

        // add the diffuse and specular light to the resulting light
        sum + diffuse.perComponentMul(sphere.material.diffuse) + specular.perComponentMul(sphere.material.specular)
      }
      else
        sum
    }
  }

  /** renders one pixel of the image */
  def renderPixel(x: Int, y: Int) = {
    // Perspective projection ray
    // Camera position is always 0, the camera looks in positive z direction
    // Other configurations would make algorithm much more complex
    // In most of 3D renderers, camera always stays in the same place, the world is moved and rotated instead
    val ray = new Ray(Vector3(0, 0, 0), Vector3(x - width/2, height/2 - y, renderPlane))

    val (distance, sphere) = traceRay(ray)
    if (sphere == null)
      0
    else
      colorToInt(applyShading(ray, distance, sphere))
  }

  /** Render all pixels and pass their colors to callback
    *
    * Not very efficient, but very simple
    */
  def render(callback: (Int, Int, Int) => Unit) =
    for (i <- 0 until width; j <- 0 until height)
      callback(i, j, renderPixel(i, j))
}
