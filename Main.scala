import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.math._

object Main {
  def main(args: Array[String]) {
    val scene = new Scene(1920, 1080, 2 * Pi / 3)
    scene.addSphere(1, 1, 4.5, 0.5, 0xffffff)
    scene.addSphere(0, -1, 5, 1.5, 0xffffff)
    scene.ambientLight = Vector3(0.1, 0.1, 0.1)
    scene.addLight(1, 5, 5, 0xffffff)

    val img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB)
    scene.render(img.setRGB _)

    ImageIO.write(img, "PNG", new File("result.png"))
  }
}
