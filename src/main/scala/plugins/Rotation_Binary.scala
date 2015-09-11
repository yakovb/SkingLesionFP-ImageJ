import core.InteropImageJ._
import dermatological.binary_ops.Rotation
import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.plugin.filter.PlugInFilter._
import ij.process.ImageProcessor

class Rotation_Binary extends PlugInFilter {
  override def setup(arg: String, imp: ImagePlus): Int =
    DOES_8G

  override def run(ip: ImageProcessor): Unit = {
    val src = getByteParImage(ip)
    val rotated = Rotation.rotate (src)
    makeImagePlus("Rotated image", makeGreyProcessor(rotated)) show()
  }
}

