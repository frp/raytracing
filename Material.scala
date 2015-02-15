case class Material(ambient: Vector3, diffuse: Vector3, specular: Vector3, shininess: Double)

object Material {
  def apply(color: Vector3, specular: Vector3, shininess: Double):Material = Material(color, color, specular, shininess)
}
