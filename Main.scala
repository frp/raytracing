import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.math._
import MathTools._

object Main {
  def squareDemo {
    render(
      List(
        Triangle(Vector3(-2, -2, 3), Vector3(-2, 2, 3), Vector3(2, -2, 3), Material(intToColor(0xcccccc), intToColor(0xffffff), 100)),
        Triangle(Vector3(2, -2, 3), Vector3(-2, 2, 3), Vector3(2, 2, 3), Material(intToColor(0xcccccc), intToColor(0xffffff), 100))
      ),
      List(Light(Vector3(0, 0, 0), intToColor(0xffffff))),
      Vector3(0, 0, 0))
  }

  def sphereDemo {
    render(
      List(
        Sphere(1, 1, 4.5, 0.5, 0xffff00, 0xffffff, 20),
        Sphere(0, -1, 5, 1.5, 0xffff00, 0xffffff, 20)
      ),
      List(Light(Vector3(1,5,5), intToColor(0xffffff))),
      Vector3(0.2, 0.2, 0.2)
    )
  }

  def maxDemo {
    render(
      List(
        Triangle(Vector3(-10, -2, 0), Vector3(-10, -2, 10), Vector3(10, -2, 0), Material(intToColor(0xcccccc), intToColor(0xffffff), 100)),
        Triangle(Vector3(10, -2, 10), Vector3(10, -2, 0), Vector3(-10, -2, 10), Material(intToColor(0xcccccc), intToColor(0xffffff), 100)),
        Sphere(0, -1, 3, 1, 0xffffff, 0xffffff, 100)
      ),
      List(Light(Vector3(-5, 1, 0), intToColor(0xffffff))),
      Vector3(0.3, 0.3, 0.3)
    )
  }

  def render(shapes: List[Shape], lights: List[Light], ambient: Vector3) {
    val scene = new Scene(1920, 1080, 2 * Pi / 3)

    scene.shapes = shapes
    scene.lights = lights

    scene.ambientLight = ambient

    val img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB)
    scene.render(img.setRGB _)

    ImageIO.write(img, "PNG", new File("result.png"))
  }

  def main(args: Array[String]) {
    if (args.length == 0)
      maxDemo
    else args(0) match {
      case "square" => squareDemo
      case "sphere" => sphereDemo
      case _ => println("Unknown demo")
    }
  }
}
