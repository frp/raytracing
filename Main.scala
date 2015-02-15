import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.math._
import MathTools._
import GraphicsTools._

object Main {
  def squareDemo {
    val v1 = Vector3(-2, -2, 3)
    val v2 = Vector3(-2, 2, 3)
    val v3 = Vector3(2, -2, 3)
    val v4 = Vector3(2, 2, 3)

    val t1 = Vector2(0, 0)
    val t2 = Vector2(0, 1)
    val t3 = Vector2(1, 0)
    val t4 = Vector2(1, 1)

    val tex = readTexture("tex.jpg")
    val mat = Material(intToColor(0xcccccc), intToColor(0xffffff), 100, 0, tex)

    render(
      List(
        Triangle(v1, v2, v3, t1, t2, t3, mat),
        Triangle(v3, v2, v4, t3, t2, t4, mat)
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
    val v1 = Vector3(-10, -2, 0)
    val v2 = Vector3(-10, -2, 10)
    val v3 = Vector3(10, -2, 0)
    val v4 = Vector3(10, -2, 10)

    val t1 = Vector2(0, 0)
    val t2 = Vector2(0, 3)
    val t3 = Vector2(3, 0)
    val t4 = Vector2(3, 3)

    val tex1 = readTexture("tex.jpg")
    val tex2 = readTexture("earth.jpg")
    val mat1 = Material(intToColor(0xcccccc), intToColor(0xffffff), 100, 0.3, tex1)
    val mat2 = Material(intToColor(0xcccccc), intToColor(0xffffff), 100, 0.3, tex2)

    render(
      List(
        Triangle(v1, v2, v3, t1, t2, t3, mat1),
        Triangle(v4, v3, v2, t4, t3, t2, mat1),
        Sphere(0, 0, 10, 3, 0xff88ff, 0xffffff, 100, 0.7),
        Sphere(-1, -1, 5, 1, 0xffffff, 0xffffff, 100, 0.5),
        Sphere(1, -1, 5, 1, mat2)
      ),
      List(Light(Vector3(-5, 1, 0), intToColor(0xffffff))),
      Vector3(0.3, 0.3, 0.3)
    )
  }

  def render(shapes: List[Shape], lights: List[Light], ambient: Vector3) {
    val scene = new Scene(1920, 1080, Pi / 2, 5)

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
