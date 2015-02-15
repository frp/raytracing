case class Material(ambient: Vector3, diffuse: Vector3, specular: Vector3, shininess: Double, reflection: Double, texture: Array[Array[Vector3]]) {
  def textureColor(c: Vector2) = {
    val coordX = ((c.x % 1) * (texture.length - 1))
    val coordY = ((c.y % 1) * (texture(0).length - 1))
    texture(coordX.toInt)(coordY.toInt)
  }
}

object Material {
  def apply(color: Vector3, specular: Vector3, shininess: Double, reflection: Double):Material =
    Material(color, color, specular, shininess, reflection, null)
  def apply(color: Vector3, specular: Vector3, shininess: Double, reflection: Double, texture: Array[Array[Vector3]]):Material =
      Material(color, color, specular, shininess, reflection, texture)
}
