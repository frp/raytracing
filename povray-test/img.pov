//EXAMPLE OF SPHERE

//Files with predefined colors and textures
#include "colors.inc"
#include "glass.inc"
#include "golds.inc"
#include "metals.inc"
#include "stones.inc"
#include "woods.inc"

//Place the camera
camera {
  location <0,0,0>
  look_at <0,0,5>
  angle 120
}

global_settings { ambient_light 10000000.0 }

sphere { <0,5,5>, 1 pigment {color White} }
sphere { <0,0,4>, 0.5 pigment {color Red} }
