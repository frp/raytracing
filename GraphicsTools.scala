import java.io.File
import javax.imageio.ImageIO
import MathTools._

object GraphicsTools {
  def readTexture(file: String) = {
    val texture = ImageIO.read(new File(file))
    val result = Array.ofDim[Vector3](texture.getWidth, texture.getHeight)
    for (i <- 0 until texture.getWidth; j <- 0 until texture.getHeight)
      result(i)(j) = intToColor(texture.getRGB(i, j))
    result
  }
}
