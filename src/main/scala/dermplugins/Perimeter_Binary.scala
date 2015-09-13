import core.InteropImageJ._
import dermatological.other_ops.Perimeter
import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.plugin.filter.PlugInFilter._
import ij.process.ImageProcessor

class Perimeter_Binary extends PlugInFilter {
  override def setup(arg: String, imp: ImagePlus): Int =
    DOES_8G

  override def run(ip: ImageProcessor): Unit = {
    val src = getByteParImage(ip)
    val result = Perimeter.markPerimeter (src)
    makeImagePlus("Marked Perimeter", makeGreyProcessor(result)) show()
  }

}