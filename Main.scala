import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.math._
import MathTools._

object Main {
  def squareDemo {
    val scene = new Scene(1920, 1080, 2 * Pi / 3)
    val t1 = Triangle(Vector3(-2, -2, 3), Vector3(-2, 2, 3), Vector3(2, -2, 3), Material(intToColor(0xcccccc), intToColor(0xffffff), 100))
    val t2 = Triangle(Vector3(2, -2, 3), Vector3(-2, 2, 3), Vector3(2, 2, 3), Material(intToColor(0xcccccc), intToColor(0xffffff), 100))
    scene.addShape(t1)
    scene.addShape(t2)

    scene.addLight(0, 0, 0, 0xffffff)

    val img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB)
    scene.render(img.setRGB _)

    ImageIO.write(img, "PNG", new File("result.png"))
  }

  def sphereDemo {
    val scene = new Scene(1920, 1080, 2 * Pi / 3)

    scene.addShape(Sphere(1, 1, 4.5, 0.5, 0xffff00, 0xffffff, 20))
    scene.addShape(Sphere(0, -1, 5, 1.5, 0xffff00, 0xffffff, 20))

    scene.ambientLight = Vector3(0.2, 0.2, 0.2)
    scene.addLight(1, 5, 5, 0xffffff)

    val img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB)
    scene.render(img.setRGB _)

    ImageIO.write(img, "PNG", new File("result.png"))
  }

  def main(args: Array[String]) {
    if (args.length == 0)
      sphereDemo
    else
      squareDemo
  }
}
