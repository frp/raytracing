import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.math._

object Main {
  def main(args: Array[String]) {
    val scene = new Scene(640, 480, 2 * Pi / 3)
    scene.addSphere(0, 5, 5, 1, 0xffffff)
    scene.addSphere(0, 0, 1, 0.5, 0xee1111)

    scene.ambientLight = Vector3(0.1, 0.1, 0.1)

    scene.addLight(5, 0, 0, 0xffff00)
    scene.addLight(-5, 0, 0, 0x0000ff)

    val img = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB)
    scene.render(img.setRGB _)

    ImageIO.write(img, "PNG", new File("result.png"))
  }
}
